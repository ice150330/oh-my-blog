<template>
  <!-- 联系页 -->
  <div class="contact-page">
    <div class="container">
      <header class="page-header">
        <h1 class="page-title">联系我</h1>
        <p class="page-subtitle">有项目合作或技术交流？欢迎联系</p>
      </header>

      <div class="contact-grid">
        <!-- 联系信息 -->
        <div class="contact-info card-glass">
          <h2 class="info-title">联系方式</h2>
          <div class="info-list">
            <a href="mailto:hello@example.com" class="info-item">
              <div class="info-icon">
                <IconSprite name="mail" :size="20" />
              </div>
              <div class="info-content">
                <span class="info-label">邮箱</span>
                <span class="info-value">hello@example.com</span>
              </div>
            </a>
            <a
              href="https://github.com"
              target="_blank"
              rel="noopener noreferrer"
              class="info-item"
            >
              <div class="info-icon">
                <IconSprite name="github" :size="20" />
              </div>
              <div class="info-content">
                <span class="info-label">GitHub</span>
                <span class="info-value">github.com/username</span>
              </div>
            </a>
          </div>

          <div class="social-links">
            <span class="social-label">社交媒体</span>
            <div class="social-list">
              <a href="#" class="social-item" aria-label="RSS">
                <IconSprite name="rss" :size="18" />
              </a>
            </div>
          </div>
        </div>

        <!-- 联系表单 -->
        <form class="contact-form card-glass" @submit.prevent="handleSubmit">
          <h2 class="form-title">发送消息</h2>
          <div class="form-group">
            <label for="name">姓名</label>
            <input
              id="name"
              v-model="form.name"
              type="text"
              placeholder="你的名字"
              required
            />
          </div>
          <div class="form-group">
            <label for="email">邮箱</label>
            <input
              id="email"
              v-model="form.email"
              type="email"
              placeholder="your@email.com"
              required
            />
          </div>
          <div class="form-group">
            <label for="message">消息</label>
            <textarea
              id="message"
              v-model="form.message"
              rows="5"
              placeholder="你想说什么..."
              required
            />
          </div>
          <button type="submit" class="btn-glow btn-submit" :disabled="isSubmitting">
            <IconSprite v-if="isSubmitting" name="refresh" :size="16" class-name="spin" />
            <IconSprite v-else name="arrow-right" :size="16" />
            {{ isSubmitting ? '发送中...' : '发送消息' }}
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
useHead({
  title: '联系我 — Oh My Blog',
})

const form = reactive({
  name: '',
  email: '',
  message: '',
})

const isSubmitting = ref(false)

async function handleSubmit() {
  isSubmitting.value = true
  try {
    // TODO: 对接后端消息 API
    await new Promise(resolve => setTimeout(resolve, 1000))
    alert('消息已发送！我会尽快回复。')
    form.name = ''
    form.email = ''
    form.message = ''
  } finally {
    isSubmitting.value = false
  }
}
</script>

<style scoped lang="scss">
.contact-page {
  padding: calc(64px + var(--space-3xl)) 0 var(--space-3xl);
}

.page-header {
  text-align: center;
  margin-bottom: var(--space-2xl);
}

.page-title {
  font-size: var(--font-size-display);
  font-weight: 700;
  color: var(--color-foreground);
}

.page-subtitle {
  font-size: var(--font-size-body-lg);
  color: var(--color-muted);
  margin-top: var(--space-sm);
}

.contact-grid {
  display: grid;
  gap: var(--space-xl);
  max-width: 900px;
  margin: 0 auto;

  @media (min-width: 768px) {
    grid-template-columns: 1fr 1.5fr;
  }
}

// 联系信息
.contact-info {
  padding: var(--space-xl);
  height: fit-content;
}

.info-title {
  font-size: var(--font-size-title);
  font-weight: 600;
  color: var(--color-foreground);
  margin-bottom: var(--space-lg);
}

.info-list {
  display: flex;
  flex-direction: column;
  gap: var(--space-md);
}

.info-item {
  display: flex;
  align-items: center;
  gap: var(--space-md);
  padding: var(--space-md);
  border-radius: var(--radius-md);
  transition: background var(--duration-fast);
  text-decoration: none;

  &:hover {
    background: rgba(59, 130, 246, 0.05);
  }
}

.info-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 44px;
  height: 44px;
  border-radius: var(--radius-md);
  background: rgba(59, 130, 246, 0.08);
  color: var(--color-primary);
  flex-shrink: 0;
}

.info-content {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.info-label {
  font-size: var(--font-size-caption);
  color: var(--color-muted);
}

.info-value {
  font-size: var(--font-size-body);
  color: var(--color-foreground);
  font-weight: 500;
}

.social-links {
  margin-top: var(--space-xl);
  padding-top: var(--space-xl);
  border-top: 1px solid var(--color-border);
}

.social-label {
  font-size: var(--font-size-caption);
  color: var(--color-muted);
  margin-bottom: var(--space-sm);
  display: block;
}

.social-list {
  display: flex;
  gap: var(--space-sm);
}

.social-item {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: var(--radius-md);
  border: 1px solid var(--color-border);
  color: var(--color-muted);
  transition: all var(--duration-fast);

  &:hover {
    color: var(--color-primary);
    border-color: var(--color-primary);
    box-shadow: var(--glow-primary);
  }
}

// 联系表单
.contact-form {
  padding: var(--space-xl);
}

.form-title {
  font-size: var(--font-size-title);
  font-weight: 600;
  color: var(--color-foreground);
  margin-bottom: var(--space-lg);
}

.form-group {
  margin-bottom: var(--space-md);

  label {
    display: block;
    font-size: var(--font-size-caption);
    font-weight: 500;
    color: var(--color-muted);
    margin-bottom: var(--space-xs);
  }

  input,
  textarea {
    width: 100%;
    background: var(--color-surface-elevated);
  }

  textarea {
    resize: vertical;
    min-height: 120px;
  }
}

.btn-submit {
  width: 100%;
  justify-content: center;
  margin-top: var(--space-sm);

  &:disabled {
    opacity: 0.7;
    cursor: not-allowed;
  }
}

.spin {
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>
