// API 客户端封装 —— 基于 $fetch，自动解包 Result<T>

/**
 * 解析后端 JSON 字符串数组字段
 */
function parseJsonArray(str: string | null | undefined): string[] {
  if (!str) return []
  try {
    const parsed = JSON.parse(str)
    return Array.isArray(parsed) ? parsed : []
  } catch {
    return []
  }
}

/**
 * 通用请求封装 —— 自动解包 Result<T>
 */
async function request<T>(url: string, options?: Parameters<typeof $fetch>[1]): Promise<T> {
  const config = useRuntimeConfig()
  const apiBase = config.public.apiBase

  const response = await $fetch<{
    code: number
    message: string
    data: T
    timestamp: number
  }>(url, {
    baseURL: apiBase,
    ...options,
    onResponseError({ response }) {
      console.error('API 错误:', response.status, response._data)
      throw new Error(response._data?.message || '请求失败')
    },
  })

  if (response.code !== 200) {
    throw new Error(response.message || '请求失败')
  }

  return response.data
}

// ==================== 文章 API ====================

export function useArticleApi() {
  return {
    // 获取文章列表（分页）
    getList: async (page = 1, size = 10, tag?: string) => {
      const result = await request<any>(
        `/articles?page=${page}&size=${size}${tag ? `&tag=${tag}` : ''}`
      )
      // 解析 tags JSON 字符串
      if (result?.records) {
        result.records.forEach((article: any) => {
          article.tags = parseJsonArray(article.tags)
          article.cover = article.coverUrl || article.cover || ''
          // 估算阅读时长（按每分钟 500 字计算）
          article.readTime = article.readTime || Math.max(1, Math.ceil((article.content?.length || 0) / 500))
        })
      }
      return result
    },

    // 获取文章详情（根据 slug，同时阅读量 +1）
    getBySlug: async (slug: string) => {
      const article = await request<any>(`/articles/${slug}`)
      article.tags = parseJsonArray(article.tags)
      article.cover = article.coverUrl || article.cover || ''
      article.readTime = article.readTime || Math.max(1, Math.ceil((article.content?.length || 0) / 500))
      return article
    },

    // 获取所有标签
    getTags: () => request<string[]>('/articles/tags'),

    // 获取按月归档
    getArchives: () => request('/articles/archives'),
  }
}

// ==================== 项目 API ====================

export function useProjectApi() {
  return {
    // 获取项目列表（分页）
    getList: async (page = 1, size = 10, category?: string) => {
      const result = await request<any>(
        `/projects?page=${page}&size=${size}${category ? `&category=${category}` : ''}`
      )
      if (result?.records) {
        result.records.forEach((project: any) => {
          project.techStack = parseJsonArray(project.techStack)
          project.highlights = parseJsonArray(project.highlights)
          // 字段映射：后端 → 前端
          project.description = project.summary || project.description || ''
          project.cover = project.coverUrl || project.cover || ''
          project.iframeDemo = project.demoUrl || project.iframeDemo || ''
          project.demoUrl = project.demoUrl || project.iframeDemo || ''
          project.tags = project.highlights?.length ? project.highlights : [project.category].filter(Boolean)
          project.featured = project.featured ?? false
        })
      }
      return result
    },

    // 获取项目详情（根据 slug）
    getBySlug: async (slug: string) => {
      const project = await request<any>(`/projects/${slug}`)
      project.techStack = parseJsonArray(project.techStack)
      project.highlights = parseJsonArray(project.highlights)
      project.description = project.summary || project.description || ''
      project.cover = project.coverUrl || project.cover || ''
      project.iframeDemo = project.demoUrl || project.iframeDemo || ''
      project.demoUrl = project.demoUrl || project.iframeDemo || ''
      project.tags = project.highlights?.length ? project.highlights : [project.category].filter(Boolean)
      project.featured = project.featured ?? false
      return project
    },

    // 获取所有分类
    getCategories: () => request<string[]>('/projects/categories'),
  }
}

// ==================== 访客统计 API ====================

export function useVisitorApi() {
  return {
    // SSE 订阅实时在线人数
    subscribeSse: (visitorId = 'anonymous') => {
      const config = useRuntimeConfig()
      const apiBase = config.public.apiBase
      return new EventSource(`${apiBase}/visitor/sse?visitorId=${visitorId}`)
    },

    // 发送心跳
    heartbeat: (visitorId = 'anonymous') =>
      request('/visitor/heartbeat', {
        method: 'POST',
        params: { visitorId },
      }),

    // 记录页面浏览量（PV）
    recordPv: () => request('/visitor/pv', { method: 'POST' }),

    // 获取最近 N 天访问统计
    getStats: (days = 7) => request(`/visitor/stats?days=${days}`),

    // 获取总访问统计
    getTotal: () => request('/visitor/total'),
  }
}

// ==================== 认证 API ====================

export function useAuthApi() {
  return {
    // 管理员登录
    login: (username: string, password: string) =>
      request('/admin/login', {
        method: 'POST',
        body: { username, password },
      }),
  }
}

// ==================== 技术栈 API ====================

export function useTechStackApi() {
  return {
    // 获取技术栈（按分类分组）
    getGrouped: () => request('/techstack'),

    // 获取雷达图数据
    getRadar: () => request('/techstack/radar'),

    // 获取技术栈列表（不分组）
    getList: () => request('/techstack/list'),
  }
}
