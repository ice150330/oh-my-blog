import { ref, watch } from 'vue'
import { defineStore } from 'pinia'

type ThemeMode = 'light' | 'dark'

function getInitialTheme(): ThemeMode {
  if (typeof window !== 'undefined') {
    const stored = localStorage.getItem('theme') as ThemeMode | null
    if (stored) return stored
    if (window.matchMedia('(prefers-color-scheme: dark)').matches) return 'dark'
  }
  return 'light'
}

export const useThemeStore = defineStore('theme', () => {
  const mode = ref<ThemeMode>(getInitialTheme())

  watch(
    mode,
    (val) => {
      if (typeof document !== 'undefined') {
        document.documentElement.setAttribute('data-theme', val)
        localStorage.setItem('theme', val)
      }
    },
    { immediate: true }
  )

  function toggle() {
    mode.value = mode.value === 'light' ? 'dark' : 'light'
  }

  return { mode, toggle }
})
