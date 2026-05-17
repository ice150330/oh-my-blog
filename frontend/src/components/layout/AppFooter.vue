<script setup lang="ts">
const navLinks = [
  { to: '/', label: '首页' },
  { to: '/projects', label: '项目' },
  { to: '/blog', label: '博客' },
  { to: '/about', label: '关于' },
]
</script>

<template>
  <footer class="border-t" style="background: var(--color-bg); border-color: var(--color-border);">
    <div class="max-w-7xl mx-auto px-6 py-16">
      <div class="grid grid-cols-3 gap-12">
        <div>
          <h3 class="text-lg font-bold mb-2" style="color: var(--color-text-1);">oh-my-blog</h3>
          <p class="text-sm" style="color: var(--color-text-2);">面向数据工程师的现代博客,记录从代码到产品的思考。</p>
        </div>
        <div>
          <h4 class="text-sm font-semibold mb-4" style="color: var(--color-text-1);">导航</h4>
          <div class="flex flex-col gap-2">
            <RouterLink
              v-for="link in navLinks"
              :key="link.to"
              :to="link.to"
              class="text-sm transition-colors hover:opacity-80"
              style="color: var(--color-text-2);"
            >
              {{ link.label }}
            </RouterLink>
          </div>
        </div>
        <div>
          <h4 class="text-sm font-semibold mb-4" style="color: var(--color-text-1);">联系</h4>
          <div class="flex gap-3">
            <a href="#" class="w-9 h-9 rounded-lg flex items-center justify-center border" style="border-color: var(--color-border);">
              <span class="text-xs" style="color: var(--color-text-2);">GH</span>
            </a>
            <a href="#" class="w-9 h-9 rounded-lg flex items-center justify-center border" style="border-color: var(--color-border);">
              <span class="text-xs" style="color: var(--color-text-2);">Mail</span>
            </a>
          </div>
        </div>
      </div>
      <div class="mt-12 pt-6 border-t text-center text-xs" style="border-color: var(--color-border); color: var(--color-text-3);">
        © 2026 Wei. 保留所有权利。 · 由 Vue 3 + Vite 驱动构建
      </div>
    </div>
  </footer>
</template>
