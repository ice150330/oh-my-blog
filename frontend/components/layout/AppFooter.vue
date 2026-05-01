<template>
  <!-- 页脚 —— 简洁信息展示 -->
  <footer class="app-footer">
    <div class="container footer-inner">
      <div class="footer-main">
        <div class="footer-brand">
          <span class="brand-name">Oh My Blog</span>
          <p class="brand-desc">基于光影设计语言的个人博客系统</p>
        </div>

        <div class="footer-links">
          <div class="link-group">
            <h4 class="link-title">导航</h4>
            <NuxtLink v-for="item in navLinks" :key="item.path" :to="item.path" class="footer-link">
              {{ item.label }}
            </NuxtLink>
          </div>
          <div class="link-group">
            <h4 class="link-title">更多</h4>
            <a
              v-for="link in externalLinks"
              :key="link.url"
              :href="link.url"
              target="_blank"
              rel="noopener noreferrer"
              class="footer-link"
            >
              <IconSprite v-if="link.icon" :name="link.icon" :size="14" />
              {{ link.label }}
            </a>
          </div>
        </div>
      </div>

      <div class="footer-bottom">
        <p class="copyright">
          © {{ currentYear }} Oh My Blog. All rights reserved.
        </p>
        <p class="tech-stack">
          Built with Nuxt 3 + Vue 3 + Tailwind CSS
        </p>
      </div>
    </div>
  </footer>
</template>

<script setup lang="ts">
const navLinks = [
  { path: '/', label: '首页' },
  { path: '/about', label: '关于' },
  { path: '/blog', label: '博客' },
  { path: '/projects', label: '项目' },
  { path: '/contact', label: '联系' },
]

const externalLinks = [
  { label: 'GitHub', url: 'https://github.com', icon: 'github' as const },
  { label: 'RSS', url: '/rss.xml', icon: 'rss' as const },
]

const currentYear = new Date().getFullYear()
</script>

<style scoped lang="scss">
.app-footer {
  background: var(--color-surface);
  border-top: 1px solid var(--color-border);
  padding: var(--space-3xl) 0 var(--space-xl);
}

.footer-inner {
  display: flex;
  flex-direction: column;
  gap: var(--space-2xl);
}

.footer-main {
  display: flex;
  flex-direction: column;
  gap: var(--space-2xl);

  @media (min-width: 768px) {
    flex-direction: row;
    justify-content: space-between;
  }
}

.footer-brand {
  .brand-name {
    font-family: var(--font-display);
    font-size: var(--font-size-headline);
    color: var(--color-foreground);
  }

  .brand-desc {
    margin-top: var(--space-sm);
    font-size: var(--font-size-caption);
    color: var(--color-muted);
  }
}

.footer-links {
  display: flex;
  gap: var(--space-3xl);
}

.link-group {
  display: flex;
  flex-direction: column;
  gap: var(--space-sm);
}

.link-title {
  font-size: var(--font-size-caption);
  font-weight: 600;
  color: var(--color-foreground);
  text-transform: uppercase;
  letter-spacing: 0.05em;
  margin-bottom: var(--space-xs);
}

.footer-link {
  display: flex;
  align-items: center;
  gap: var(--space-xs);
  font-size: var(--font-size-body);
  color: var(--color-muted);
  transition: color var(--duration-fast);

  &:hover {
    color: var(--color-primary);
  }
}

.footer-bottom {
  display: flex;
  flex-direction: column;
  gap: var(--space-xs);
  padding-top: var(--space-xl);
  border-top: 1px solid var(--color-border);
  text-align: center;

  @media (min-width: 768px) {
    flex-direction: row;
    justify-content: space-between;
    text-align: left;
  }
}

.copyright,
.tech-stack {
  font-size: var(--font-size-caption);
  color: var(--color-muted);
}
</style>
