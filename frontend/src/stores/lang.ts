import { defineStore } from 'pinia'
import { ref, watch } from 'vue'
import { useI18n } from 'vue-i18n'

export type Locale = 'zh' | 'en'

function getSavedLocale(): Locale {
  if (typeof localStorage === 'undefined') return 'zh'
  const saved = localStorage.getItem('locale')
  return saved === 'en' || saved === 'zh' ? saved : 'zh'
}

export const useLangStore = defineStore('lang', () => {
  const locale = ref<Locale>(getSavedLocale())

  // 注意：需要在组件挂载后才能调用 useI18n
  // 这里用 watch 来同步
  watch(locale, (val) => {
    localStorage.setItem('locale', val)
    // 在 main.ts 中会同步设置 i18n.global.locale
  })

  function toggle() {
    locale.value = locale.value === 'zh' ? 'en' : 'zh'
  }

  function setLang(val: Locale) {
    locale.value = val
  }

  return { locale, toggle, setLang }
})

// 辅助 composable：在组件中同步 i18n locale
export function useSyncLocale() {
  const { locale } = useI18n()
  const langStore = useLangStore()

  watch(
    () => langStore.locale,
    (val) => {
      locale.value = val
    },
    { immediate: true }
  )

  return langStore
}
