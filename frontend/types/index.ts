// ============================================================
// 全局类型定义
// ============================================================

// 文章类型
export interface Article {
  id: number
  title: string
  slug: string
  summary: string
  content: string
  coverUrl?: string
  cover?: string // 映射字段
  tags: string[]
  category?: string
  readTime: number // 分钟
  viewCount: number
  createdAt: string
  updatedAt?: string
}

// 项目类型
export interface Project {
  id: number
  title: string
  slug: string
  summary?: string // 后端原始字段
  description: string // 映射字段（等同于 summary）
  coverUrl?: string // 后端原始字段
  cover: string // 映射字段
  category?: string
  tags: string[]
  techStack: string[]
  highlights?: string[] // 后端原始字段
  demoUrl?: string // 后端原始字段
  iframeDemo?: string // 映射字段
  repoUrl?: string
  featured: boolean
  status?: number
  createdAt: string
}

// 统计数据类型
export interface Statistics {
  articleCount: number
  projectCount: number
  viewCount: number
  runDays: number
}

// 访客记录
export interface VisitorRecord {
  id?: number
  ip: string
  userAgent: string
  path: string
  visitedAt: string
}

// API 响应包装
export interface ApiResponse<T> {
  code: number
  message: string
  data: T
  timestamp: number
}

// 分页响应 —— 匹配后端 MyBatis Plus Page 结构
export interface PageResponse<T> {
  records: T[]
  total: number
  size: number
  current: number
  pages: number
}
