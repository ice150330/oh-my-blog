import { createI18n } from 'vue-i18n'
import zh from './zh.json'
import en from './en.json'

const messages = { zh, en }

function getInitialLocale(): string {
  if (typeof localStorage !== 'undefined') {
    const saved = localStorage.getItem('locale')
    if (saved && saved in messages) return saved
  }
  if (typeof navigator !== 'undefined') {
    const lang = navigator.language.toLowerCase()
    if (lang.startsWith('zh')) return 'zh'
    if (lang.startsWith('en')) return 'en'
  }
  return 'zh'
}

const i18n = createI18n({
  legacy: false,
  locale: getInitialLocale(),
  fallbackLocale: 'zh',
  messages,
})

export default i18n
