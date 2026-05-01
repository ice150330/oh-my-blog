<template>
  <!-- 默认布局 —— 玻璃拟态背景 + 全局字体 -->
  <div class="app-layout">
    <AppHeader />
    <main class="app-main">
      <slot />
    </main>
    <AppFooter />
  </div>
</template>

<script setup lang="ts">
// 布局级全局初始化
useHead({
  htmlAttrs: {
    lang: 'zh-CN',
  },
})

const { recordPv, heartbeat } = useVisitorApi()
const visitorId = useVisitorId()

// 生成访客唯一标识
function useVisitorId(): string {
  if (typeof window === 'undefined') return 'anonymous'
  let id = localStorage.getItem('visitor_id')
  if (!id) {
    id = 'v_' + Math.random().toString(36).substring(2, 15)
    localStorage.setItem('visitor_id', id)
  }
  return id
}

// 记录页面访问
onMounted(() => {
  // 记录 PV（忽略错误）
  recordPv().catch(() => {})

  // 发送心跳（每 20 秒一次）
  heartbeat(visitorId).catch(() => {})
  const heartbeatInterval = setInterval(() => {
    heartbeat(visitorId).catch(() => {})
  }, 20000)

  onBeforeUnmount(() => {
    clearInterval(heartbeatInterval)
  })
})
</script>

<style scoped lang="scss">
.app-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: var(--color-background);
  // 微妙的背景纹理（可选）
  background-image:
    radial-gradient(circle at 20% 50%, rgba(59, 130, 246, 0.03) 0%, transparent 50%),
    radial-gradient(circle at 80% 80%, rgba(96, 165, 250, 0.03) 0%, transparent 50%);
}

.app-main {
  flex: 1;
  width: 100%;
}

.dark .app-layout {
  background-image:
    radial-gradient(circle at 20% 50%, rgba(96, 165, 250, 0.05) 0%, transparent 50%),
    radial-gradient(circle at 80% 80%, rgba(59, 130, 246, 0.04) 0%, transparent 50%);
}
</style>
