import { defineStore } from 'pinia'

// 主题类型
export type Theme = 'light' | 'dark' | 'system'

// 主题状态管理 —— 持久化到 localStorage
export const useThemeStore = defineStore('theme', () => {
  // 状态
  const theme = ref<Theme>('system')
  const isDark = ref(false)

  // 初始化主题
  function initTheme() {
    // 读取本地存储
    const stored = localStorage.getItem('theme') as Theme | null
    if (stored && ['light', 'dark', 'system'].includes(stored)) {
      theme.value = stored
    }
    applyTheme()

    // 监听系统主题变化（仅在 system 模式下）
    const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)')
    mediaQuery.addEventListener('change', () => {
      if (theme.value === 'system') {
        applyTheme()
      }
    })
  }

  // 应用主题到 DOM
  function applyTheme() {
    const root = document.documentElement
    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches

    if (theme.value === 'dark' || (theme.value === 'system' && prefersDark)) {
      isDark.value = true
      root.classList.add('dark')
    } else {
      isDark.value = false
      root.classList.remove('dark')
    }
  }

  // 设置主题
  function setTheme(newTheme: Theme) {
    theme.value = newTheme
    localStorage.setItem('theme', newTheme)
    applyTheme()
  }

  // 切换亮/暗
  function toggleDark() {
    setTheme(isDark.value ? 'light' : 'dark')
  }

  return {
    theme,
    isDark,
    initTheme,
    setTheme,
    toggleDark,
  }
})
