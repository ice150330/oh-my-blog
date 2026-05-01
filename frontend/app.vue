<template>
  <!-- 全局应用根组件 —— 由 AppLayout 包裹所有页面 -->
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>
</template>

<script setup lang="ts">
// 全局初始化：主题偏好、访客统计等
const { initTheme } = useThemeStore()

onMounted(() => {
  // 客户端初始化主题
  initTheme()
})
</script>
