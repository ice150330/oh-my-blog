import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import pinia from './stores'
import i18n from './locales'
import { useLangStore } from './stores/lang'
import { gsap } from 'gsap'
import { ScrollTrigger } from 'gsap/ScrollTrigger'
import './styles/main.scss'

// GSAP ScrollTrigger 全局只注册一次
gsap.registerPlugin(ScrollTrigger)

const app = createApp(App)
app.use(pinia)
app.use(i18n)
app.use(router)

// 同步 lang store 与 i18n locale
const langStore = useLangStore()
i18n.global.locale.value = langStore.locale

app.mount('#app')
