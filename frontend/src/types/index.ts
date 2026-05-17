export interface Project {
  id: string
  title: string
  titleEn?: string
  category: 'ai' | 'data' | 'business' | 'algo' | 'meta'
  tags: string[]
  description: string
  descriptionEn?: string
  coverGradient: [string, string]
  demoUrl?: string
  repoUrl?: string
  slug: string
  stars?: number
  techCount?: number
}

export interface Article {
  slug: string
  title: string
  titleEn?: string
  category: string
  categoryEn?: string
  summary: string
  summaryEn?: string
  date: string
  readingTime: number
  tags: string[]
}

export interface Skill {
  name: string
  nameEn?: string
  level: number
}

export interface TimelineEvent {
  year: string
  place: string
  placeEn?: string
  title: string
  titleEn?: string
  description: string
  descriptionEn?: string
}

export interface TechCategory {
  name: string
  nameEn?: string
  sub: string
  subEn?: string
  icon: string
  tags: string[]
}
