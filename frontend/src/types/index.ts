export interface Project {
  id: string
  title: string
  category: 'ai' | 'data' | 'business' | 'algo' | 'meta'
  tags: string[]
  description: string
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
  category: string
  summary: string
  date: string
  readingTime: number
  tags: string[]
}

export interface Skill {
  name: string
  level: number
}

export interface TimelineEvent {
  year: string
  place: string
  title: string
  description: string
}

export interface TechCategory {
  name: string
  sub: string
  icon: string
  tags: string[]
}
