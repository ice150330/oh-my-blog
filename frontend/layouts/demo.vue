<template>
  <!-- Demo 展示布局 —— 全屏模式，隐藏博客默认导航 -->
  <div class="demo-layout">
    <slot />
  </div>
</template>

<style>
/* 重置博客全局样式对 Element Plus 的干扰 */
.demo-layout {
  min-height: 100vh;
  background: #f5f7fa;
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB',
    'Microsoft YaHei', Arial, sans-serif;
}

/* 隐藏博客的固定头部，避免遮挡 demo */
.demo-layout ~ .app-header,
.demo-layout ~ header.app-header {
  display: none !important;
}

/* 确保 demo 内容区域不受博客样式影响 */
.demo-layout * {
  box-sizing: border-box;
}

.demo-layout h2 {
  margin-top: 0;
  margin-bottom: 20px;
  font-size: 20px;
  font-weight: 600;
  color: #303133;
}
</style>
