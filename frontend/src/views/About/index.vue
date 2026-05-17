<script setup lang="ts">
import { onMounted } from 'vue'
import { gsap } from 'gsap'
import { ScrollTrigger } from 'gsap/ScrollTrigger'
import { useI18n } from 'vue-i18n'
import { skills, timeline, stats } from '@/data/techStack'
import SectionTitle from '@/components/ui/SectionTitle.vue'
import SkillBar from '@/components/ui/SkillBar.vue'
import StatItem from '@/components/ui/StatItem.vue'
import IconBadge from '@/components/ui/IconBadge.vue'
import TimelineNode from '@/components/ui/TimelineNode.vue'
import PrimaryButton from '@/components/ui/PrimaryButton.vue'

gsap.registerPlugin(ScrollTrigger)
const { t } = useI18n()

onMounted(() => {
  const reduced = window.matchMedia('(prefers-reduced-motion: reduce)').matches
  if (reduced) return

  gsap.from('.about-left > div', {
    y: 40, opacity: 0, duration: 0.7, stagger: 0.15, ease: 'power3.out'
  })

  ScrollTrigger.create({
    trigger: '.about-right',
    start: 'top 80%',
    once: true,
    onEnter: () => {
      gsap.from('.about-right > div', {
        y: 40, opacity: 0, duration: 0.7, stagger: 0.15, ease: 'power3.out'
      })
    }
  })
})
</script>

<template>
  <div class="max-w-[1400px] mx-auto px-6 lg:px-10 py-20">
    <div class="flex flex-col lg:flex-row gap-12">
      <div class="about-left w-full lg:w-[480px] flex flex-col gap-8">
        <div class="flex flex-col items-center gap-5 rounded-2xl p-8" style="background: var(--color-card); border: 1px solid var(--color-border);">
          <div
            class="w-[200px] h-[200px] rounded-full flex items-center justify-center text-5xl font-bold"
            style="background: radial-gradient(circle at 40% 40%, var(--mesh-tint-1), var(--mesh-tint-2)); font-family: var(--font-display); color: var(--color-primary);"
          >
            W
          </div>
          <div class="flex flex-col items-center gap-1">
            <h1 class="text-2xl font-bold" style="font-family: var(--font-display); color: var(--color-text-1);">{{ t('about.name') }}</h1>
            <p class="text-sm" style="color: var(--color-text-2);">{{ t('about.role') }}</p>
            <p class="text-xs flex items-center gap-1 mt-1" style="color: var(--color-text-3);">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><use href="/icons/sprite.svg#map-pin" /></svg>
              {{ t('about.location') }}
            </p>
          </div>
          <div class="flex items-center gap-3">
            <a href="https://github.com" target="_blank" class="transition-opacity hover:opacity-70">
              <IconBadge icon="github" />
            </a>
            <a href="mailto:wei@example.com" class="transition-opacity hover:opacity-70">
              <IconBadge icon="mail" />
            </a>
            <a href="#" target="_blank" class="transition-opacity hover:opacity-70">
              <IconBadge icon="twitter" />
            </a>
            <a href="#" target="_blank" class="transition-opacity hover:opacity-70">
              <IconBadge icon="rss" />
            </a>
          </div>
        </div>

        <div class="grid grid-cols-2 gap-4 rounded-2xl p-6" style="background: var(--color-card); border: 1px solid var(--color-border);">
          <StatItem v-for="stat in stats" :key="stat.label" :value="stat.value" :label="stat.label" />
        </div>

        <div class="flex flex-col gap-4 rounded-2xl p-6" style="background: var(--color-card); border: 1px solid var(--color-border);">
          <h3 class="text-base font-semibold" style="color: var(--color-text-1);">{{ t('about.ctaTitle') }}</h3>
          <p class="text-[13px] leading-relaxed" style="color: var(--color-text-2);">
            {{ t('about.ctaDesc') }}
          </p>
          <PrimaryButton :text="t('about.ctaEmail')" href="mailto:wei@example.com" />
        </div>
      </div>

      <div class="about-right flex-1 flex flex-col gap-12">
        <div class="flex flex-col gap-4">
          <SectionTitle en="About Me" :zh="t('about.title')" />
          <div class="flex flex-col gap-3 text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            <p>{{ t('about.intro1') }}</p>
            <p>{{ t('about.intro2') }}</p>
            <p>{{ t('about.intro3') }}</p>
          </div>
        </div>

        <div class="flex flex-col gap-5">
          <h3 class="text-xl font-bold" style="font-family: var(--font-display); color: var(--color-text-1);">{{ t('about.timeline') }}</h3>
          <div class="flex flex-col">
            <TimelineNode
              v-for="(event, idx) in timeline"
              :key="event.year"
              :year="event.year"
              :place="event.place"
              :title="event.title"
              :description="event.description"
              :last="idx === timeline.length - 1"
            />
          </div>
        </div>

        <div class="flex flex-col gap-5">
          <h3 class="text-xl font-bold" style="font-family: var(--font-display); color: var(--color-text-1);">{{ t('about.skills') }}</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-x-8 gap-y-5">
            <SkillBar
              v-for="skill in skills"
              :key="skill.name"
              :name="skill.name"
              :level="skill.level"
            />
          </div>
        </div>

        <div class="flex flex-col gap-5">
          <h3 class="text-xl font-bold" style="font-family: var(--font-display); color: var(--color-text-1);">{{ t('about.values') }}</h3>
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div class="flex flex-col gap-2 rounded-xl p-5" style="background: var(--color-card); border: 1px solid var(--color-border);">
              <IconBadge icon="zap" />
              <span class="text-sm font-semibold" style="color: var(--color-text-1);">{{ t('about.valueSimplicity') }}</span>
              <span class="text-[13px] leading-relaxed" style="color: var(--color-text-2);">{{ t('about.valueSimplicityDesc') }}</span>
            </div>
            <div class="flex flex-col gap-2 rounded-xl p-5" style="background: var(--color-card); border: 1px solid var(--color-border);">
              <IconBadge icon="eye" />
              <span class="text-sm font-semibold" style="color: var(--color-text-1);">{{ t('about.valueDataDriven') }}</span>
              <span class="text-[13px] leading-relaxed" style="color: var(--color-text-2);">{{ t('about.valueDataDrivenDesc') }}</span>
            </div>
            <div class="flex flex-col gap-2 rounded-xl p-5" style="background: var(--color-card); border: 1px solid var(--color-border);">
              <IconBadge icon="rocket" />
              <span class="text-sm font-semibold" style="color: var(--color-text-1);">{{ t('about.valueIteration') }}</span>
              <span class="text-[13px] leading-relaxed" style="color: var(--color-text-2);">{{ t('about.valueIterationDesc') }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
