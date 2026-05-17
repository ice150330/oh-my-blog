<script setup lang="ts">
import { useSyncLocale } from '@/stores/lang'

const langStore = useSyncLocale()
</script>

<template>
  <button
    class="relative h-7 rounded-full flex items-center cursor-pointer transition-colors px-1"
    style="background: var(--color-bg-elevated); border: 1px solid var(--color-border); width: 64px;"
    @click="langStore.toggle()"
    aria-label="切换语言"
  >
    <span
      class="absolute top-[2px] left-[2px] h-6 w-[28px] rounded-full transition-all duration-300"
      :style="{
        transform: langStore.locale === 'en' ? 'translateX(30px)' : 'translateX(0)',
        background: 'var(--color-primary)',
      }"
    />
    <span class="relative z-10 flex-1 text-center text-[11px] font-bold" :style="{ color: langStore.locale === 'zh' ? '#fff' : 'var(--color-text-3)' }">
      中
    </span>
    <span class="relative z-10 flex-1 text-center text-[11px] font-bold" :style="{ color: langStore.locale === 'en' ? '#fff' : 'var(--color-text-3)' }">
      EN
    </span>
  </button>
</template>
