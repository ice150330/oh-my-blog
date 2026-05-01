import { defineNuxtConfig } from 'nuxt/config'

// Nuxt 3 配置文件 —— 光影设计系统博客
export default defineNuxtConfig({
  // 启用 SSR 并预渲染关键路由
  ssr: true,

  // Nitro 预渲染配置
  nitro: {
    prerender: {
      routes: [
        '/',
        '/about',
        '/blog',
        '/projects',
        '/contact',
      ],
    },
  },

  // 应用级元数据
  app: {
    head: {
      title: 'Oh My Blog',
      meta: [
        { charset: 'utf-8' },
        { name: 'viewport', content: 'width=device-width, initial-scale=1' },
        { name: 'description', content: '一个基于光影设计语言的个人博客系统' },
      ],
      link: [
        // 预加载关键字体
        { rel: 'preload', href: '/font/SourceHanSansSC-subset.woff2', as: 'font', type: 'font/woff2', crossorigin: '' },
        { rel: 'preload', href: '/font/JetBrainsMono.woff2', as: 'font', type: 'font/woff2', crossorigin: '' },
      ],
    },
  },

  // CSS 入口 —— 先加载字体，再加载全局样式
  css: [
    '~/styles/_fonts.scss',
    '~/styles/main.scss',
  ],

  // 模块
  modules: [
    '@nuxtjs/tailwindcss',
    '@pinia/nuxt',
    '@nuxt/content',
  ],

  // Element Plus 组件自动导入
  imports: {
    dirs: ['composables', 'composables/**'],
  },

  // Tailwind CSS 配置
  tailwindcss: {
    cssPath: '~/styles/tailwind.css',
    configPath: '~/tailwind.config.ts',
    exposeConfig: false,
    viewer: false,
  },

  // Pinia 配置
  pinia: {
    storesDirs: ['./stores/**'],
  },

  // Nuxt Content 配置
  content: {
    documentDriven: false,
    markdown: {
      remarkPlugins: [],
      rehypePlugins: [],
    },
    highlight: {
      theme: 'github-dark',
    },
  },

  // 运行时配置（环境变量）
  runtimeConfig: {
    public: {
      apiBase: 'http://localhost:8080/api/v1',
    },
  },

  // 开发服务器配置
  devServer: {
    port: 3000,
    host: 'localhost',
  },

  // TypeScript 配置
  typescript: {
    typeCheck: false,
    strict: true,
  },

  // 兼容性日期
  compatibilityDate: '2024-05-01',
})
