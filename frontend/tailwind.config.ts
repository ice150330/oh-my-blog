// @ts-ignore tailwindcss 类型由 @nuxtjs/tailwindcss 提供
import type { Config } from 'tailwindcss'

// Tailwind 配置 —— 光影设计系统扩展
export default <Partial<Config>>{
  content: [
    './components/**/*.{vue,js,ts}',
    './layouts/**/*.vue',
    './pages/**/*.vue',
    './composables/**/*.{js,ts}',
    './plugins/**/*.{js,ts}',
    './app.vue',
  ],
  darkMode: 'class',
  theme: {
    extend: {
      // 色彩映射 —— 使用 CSS 变量实现主题切换
      colors: {
        primary: {
          DEFAULT: 'var(--color-primary)',
          light: 'var(--color-primary-light)',
          dark: 'var(--color-primary-dark)',
        },
        surface: {
          DEFAULT: 'var(--color-surface)',
          elevated: 'var(--color-surface-elevated)',
          overlay: 'var(--color-surface-overlay)',
        },
        background: 'var(--color-background)',
        foreground: 'var(--color-foreground)',
        muted: 'var(--color-muted)',
        border: 'var(--color-border)',
      },

      // 间距映射 —— 与 CSS 变量对齐
      spacing: {
        'xs': 'var(--space-xs)',
        'sm': 'var(--space-sm)',
        'md': 'var(--space-md)',
        'lg': 'var(--space-lg)',
        'xl': 'var(--space-xl)',
        '2xl': 'var(--space-2xl)',
        '3xl': 'var(--space-3xl)',
      },

      // 字体家族
      fontFamily: {
        body: 'var(--font-body)',
        code: 'var(--font-code)',
        display: 'var(--font-display)',
        accent: 'var(--font-accent)',
        pixel: 'var(--font-pixel)',
      },

      // 字体大小
      fontSize: {
        'display-xl': ['var(--font-size-display-xl)', { lineHeight: '1.1', letterSpacing: '-0.02em' }],
        'display': ['var(--font-size-display)', { lineHeight: '1.15', letterSpacing: '-0.02em' }],
        'headline': ['var(--font-size-headline)', { lineHeight: '1.2', letterSpacing: '-0.01em' }],
        'title': ['var(--font-size-title)', { lineHeight: '1.3' }],
        'body-lg': ['var(--font-size-body-lg)', { lineHeight: '1.6' }],
        'body': ['var(--font-size-body)', { lineHeight: '1.6' }],
        'caption': ['var(--font-size-caption)', { lineHeight: '1.5' }],
      },

      // 边框圆角
      borderRadius: {
        'sm': 'var(--radius-sm)',
        'md': 'var(--radius-md)',
        'lg': 'var(--radius-lg)',
        'xl': 'var(--radius-xl)',
        '2xl': 'var(--radius-2xl)',
        'full': 'var(--radius-full)',
      },

      // 阴影 —— 光影核心
      boxShadow: {
        'soft': 'var(--shadow-soft)',
        'md': 'var(--shadow-md)',
        'lg': 'var(--shadow-lg)',
        'glow': 'var(--glow-primary)',
        'glow-strong': 'var(--glow-primary-strong)',
      },

      // 动画
      animation: {
        'glow-pulse': 'glowPulse 3s ease-in-out infinite',
        'fade-in': 'fadeIn 0.5s ease-out',
        'slide-up': 'slideUp 0.5s ease-out',
      },

      keyframes: {
        glowPulse: {
          '0%, 100%': { boxShadow: 'var(--glow-primary)' },
          '50%': { boxShadow: 'var(--glow-primary-strong)' },
        },
        fadeIn: {
          from: { opacity: '0' },
          to: { opacity: '1' },
        },
        slideUp: {
          from: { opacity: '0', transform: 'translateY(20px)' },
          to: { opacity: '1', transform: 'translateY(0)' },
        },
      },

      // 变换缩放
      scale: {
        '103': '1.03',
      },

      // 过渡时长
      transitionDuration: {
        'fast': 'var(--duration-fast)',
        'normal': 'var(--duration-normal)',
        'slow': 'var(--duration-slow)',
      },

      // 背景图片
      backgroundImage: {
        'gradient-hero': 'var(--gradient-hero)',
        'gradient-card': 'var(--gradient-card-shine)',
      },
    },
  },
  plugins: [],
}
