<template>
  <!-- 顶部导航 —— 固定定位 + 玻璃拟态背景 -->
  <header
    class="app-header"
    :class="{ 'is-scrolled': isScrolled, 'is-hidden': isHidden }"
  >
    <div class="container header-inner">
      <!-- Logo -->
      <NuxtLink to="/" class="logo" aria-label="回到首页">
        <span class="logo-text">Oh My Blog</span>
      </NuxtLink>

      <!-- 桌面导航 -->
      <nav class="desktop-nav" aria-label="主导航">
        <NuxtLink
          v-for="item in navItems"
          :key="item.path"
          :to="item.path"
          class="nav-link"
          :class="{ 'is-active': isActive(item.path) }"
        >
          <IconSprite
            v-if="item.icon"
            :name="item.icon"
            :size="18"
            class-name="nav-icon"
          />
          {{ item.label }}
        </NuxtLink>
      </nav>

      <!-- 右侧操作区 -->
      <div class="header-actions">
        <!-- 主题切换 -->
        <button
          class="theme-toggle"
          :aria-label="isDark ? '切换到亮色模式' : '切换到暗色模式'"
          @click="toggleTheme"
        >
          <IconSprite
            :name="isDark ? 'sun' : 'moon'"
            :size="20"
          />
        </button>

        <!-- 移动端菜单按钮 -->
        <button
          class="menu-toggle"
          aria-label="打开菜单"
          @click="isMenuOpen = true"
        >
          <IconSprite name="menu" :size="24" />
        </button>
      </div>
    </div>

    <!-- 移动端菜单遮罩 -->
    <Transition name="slide">
      <div
        v-if="isMenuOpen"
        class="mobile-menu-overlay"
        @click.self="isMenuOpen = false"
      >
        <div class="mobile-menu">
          <button
            class="mobile-menu-close"
            aria-label="关闭菜单"
            @click="isMenuOpen = false"
          >
            <IconSprite name="close" :size="24" />
          </button>
          <nav class="mobile-nav" aria-label="移动端导航">
            <NuxtLink
              v-for="item in navItems"
              :key="item.path"
              :to="item.path"
              class="mobile-nav-link"
              :class="{ 'is-active': isActive(item.path) }"
              @click="isMenuOpen = false"
            >
              <IconSprite
                v-if="item.icon"
                :name="item.icon"
                :size="20"
              />
              {{ item.label }}
            </NuxtLink>
          </nav>
        </div>
      </div>
    </Transition>
  </header>
</template>

<script setup lang="ts">
// 导航项配置
const navItems = [
  { path: '/', label: '首页', icon: 'home' as const },
  { path: '/about', label: '关于', icon: 'about' as const },
  { path: '/blog', label: '博客', icon: 'article' as const },
  { path: '/projects', label: '项目', icon: 'project' as const },
  { path: '/contact', label: '联系', icon: 'mail' as const },
]

// 路由状态
const route = useRoute()
function isActive(path: string) {
  if (path === '/') {
    return route.path === '/'
  }
  return route.path.startsWith(path)
}

// 主题切换
const themeStore = useThemeStore()
const isDark = computed(() => themeStore.isDark)
function toggleTheme() {
  themeStore.toggleDark()
}

// 滚动状态
const isScrolled = ref(false)
const isHidden = ref(false)
let lastScrollY = 0

onMounted(() => {
  const handleScroll = () => {
    const currentY = window.scrollY
    isScrolled.value = currentY > 10
    // 向下滚动超过 100px 时隐藏头部，向上滚动时显示
    if (currentY > lastScrollY && currentY > 100) {
      isHidden.value = true
    } else {
      isHidden.value = false
    }
    lastScrollY = currentY
  }
  window.addEventListener('scroll', handleScroll, { passive: true })
  onBeforeUnmount(() => {
    window.removeEventListener('scroll', handleScroll)
  })
})

// 移动端菜单
const isMenuOpen = ref(false)
</script>

<style scoped lang="scss">
.app-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: var(--z-header);
  height: 64px;
  background: transparent;
  transition: background var(--duration-normal), transform var(--duration-normal);

  &.is-scrolled {
    background: var(--color-surface-overlay);
    backdrop-filter: blur(12px);
    -webkit-backdrop-filter: blur(12px);
    border-bottom: 1px solid var(--color-border);
  }

  &.is-hidden {
    transform: translateY(-100%);
  }
}

.header-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
}

.logo {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
  text-decoration: none;
}

.logo-text {
  font-family: var(--font-display);
  font-size: var(--font-size-title);
  font-weight: 700;
  color: var(--color-foreground);
  letter-spacing: -0.02em;
}

.desktop-nav {
  display: none;
  align-items: center;
  gap: var(--space-xs);

  @media (min-width: 768px) {
    display: flex;
  }
}

.nav-link {
  display: flex;
  align-items: center;
  gap: var(--space-xs);
  padding: var(--space-sm) var(--space-md);
  border-radius: var(--radius-md);
  font-size: var(--font-size-body);
  color: var(--color-muted);
  transition: color var(--duration-fast), background var(--duration-fast);

  &:hover,
  &.is-active {
    color: var(--color-primary);
    background: rgba(59, 130, 246, 0.08);
  }
}

.nav-icon {
  opacity: 0.7;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
}

.theme-toggle,
.menu-toggle {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: var(--radius-md);
  color: var(--color-muted);
  transition: color var(--duration-fast), background var(--duration-fast);

  &:hover {
    color: var(--color-primary);
    background: rgba(59, 130, 246, 0.08);
  }
}

.menu-toggle {
  @media (min-width: 768px) {
    display: none;
  }
}

// 移动端菜单
.mobile-menu-overlay {
  position: fixed;
  inset: 0;
  z-index: var(--z-modal);
  background: rgba(0, 0, 0, 0.4);
  backdrop-filter: blur(4px);
}

.mobile-menu {
  position: absolute;
  top: 0;
  right: 0;
  width: 280px;
  max-width: 80vw;
  height: 100%;
  background: var(--color-surface);
  border-left: 1px solid var(--color-border);
  padding: var(--space-xl);
  display: flex;
  flex-direction: column;
}

.mobile-menu-close {
  align-self: flex-end;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: var(--radius-md);
  color: var(--color-muted);
  margin-bottom: var(--space-lg);

  &:hover {
    color: var(--color-foreground);
    background: var(--color-border);
  }
}

.mobile-nav {
  display: flex;
  flex-direction: column;
  gap: var(--space-xs);
}

.mobile-nav-link {
  display: flex;
  align-items: center;
  gap: var(--space-md);
  padding: var(--space-md);
  border-radius: var(--radius-md);
  font-size: var(--font-size-body-lg);
  color: var(--color-muted);
  transition: color var(--duration-fast), background var(--duration-fast);

  &:hover,
  &.is-active {
    color: var(--color-primary);
    background: rgba(59, 130, 246, 0.08);
  }
}

// 移动端菜单动画
.slide-enter-active,
.slide-leave-active {
  transition: opacity 0.3s ease;
}

.slide-enter-from,
.slide-leave-to {
  opacity: 0;
}
</style>
