<script setup lang="ts">
const navLinks = [
  { to: '/', label: '首页' },
  { to: '/projects', label: '项目' },
  { to: '/blog', label: '博客' },
  { to: '/about', label: '关于' },
]
</script>

<template>
  <footer class="border-t" style="background: var(--color-bg-elevated); border-color: var(--color-border);">
    <div class="max-w-[1400px] mx-auto px-6 lg:px-10 py-16">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-12">
        <div class="flex flex-col gap-4">
          <h3 class="text-lg font-bold" style="color: var(--color-text-1);">oh-my-blog</h3>
          <p class="text-sm leading-relaxed" style="color: var(--color-text-2);">
            面向数据工程师的现代博客，记录从代码到产品的思考。
          </p>
          <div class="flex items-center gap-2 px-3 py-2 rounded-lg" style="background: var(--color-bg); border: 1px solid var(--color-border);">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round" style="color: var(--color-text-3);">
              <use href="/icons/sprite.svg#mail" />
            </svg>
            <input type="text" placeholder="订阅更新..." class="bg-transparent text-sm outline-none w-full" style="color: var(--color-text-1);" />
          </div>
        </div>
        <div>
          <h4 class="text-sm font-semibold mb-4" style="color: var(--color-text-1);">导航</h4>
          <div class="flex flex-col gap-2">
            <RouterLink
              v-for="link in navLinks"
              :key="link.to"
              :to="link.to"
              class="text-sm transition-all hover:translate-x-1"
              style="color: var(--color-text-2);"
            >
              {{ link.label }}
            </RouterLink>
          </div>
        </div>
        <div>
          <h4 class="text-sm font-semibold mb-4" style="color: var(--color-text-1);">联系</h4>
          <div class="flex gap-3">
            <a
              href="#"
              class="group w-9 h-9 rounded-lg flex items-center justify-center border transition-all hover:border-[var(--color-primary)] hover:scale-110"
              style="border-color: var(--color-border);"
            >
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round" class="transition-colors group-hover:text-[var(--color-primary)]" style="color: var(--color-text-2);">
                <use href="/icons/sprite.svg#github" />
              </svg>
            </a>
            <a
              href="#"
              class="group w-9 h-9 rounded-lg flex items-center justify-center border transition-all hover:border-[var(--color-primary)] hover:scale-110"
              style="border-color: var(--color-border);"
            >
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round" class="transition-colors group-hover:text-[var(--color-primary)]" style="color: var(--color-text-2);">
                <use href="/icons/sprite.svg#mail" />
              </svg>
            </a>
          </div>
        </div>
      </div>
      <div class="mt-12 pt-6 border-t flex flex-col sm:flex-row items-center justify-between gap-2 text-xs" style="border-color: var(--color-border); color: var(--color-text-3);">
        <span>© 2026 Wei. 保留所有权利。</span>
        <span style="font-family: var(--font-code);">由 Vue 3 + Vite 驱动构建</span>
      </div>
    </div>
  </footer>
</template>
