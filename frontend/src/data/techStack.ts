import type { Skill, TechCategory, TimelineEvent } from '@/types'

export const skills: Skill[] = [
  { name: 'Vue 3 / Nuxt 3', level: 95 },
  { name: 'React / Next.js', level: 80 },
  { name: 'TypeScript', level: 90 },
  { name: 'ECharts / D3.js', level: 85 },
  { name: 'Python / FastAPI', level: 75 },
  { name: 'AI / LLM 工程', level: 70 },
  { name: 'Tailwind / SCSS', level: 90 },
  { name: 'Node.js / Vite', level: 85 },
]

export const techCategories: TechCategory[] = [
  {
    name: '前端',
    sub: '前端工程 · 主轴',
    icon: 'layout-dashboard',
    tags: ['Vue 3', 'React', 'TypeScript', 'Tailwind', 'Vite', 'Nuxt 3'],
  },
  {
    name: '数据可视化',
    sub: '数据可视化 · 重点表达力',
    icon: 'chart-column',
    tags: ['ECharts', 'D3.js', 'Cesium', 'Canvas', 'SVG', 'WebGL'],
  },
  {
    name: '工程化',
    sub: '工程化与协作',
    icon: 'settings-2',
    tags: ['Git', 'CI/CD', 'Docker', 'Nginx', 'ESLint', 'Vitest'],
  },
  {
    name: 'AI 与数据',
    sub: '智能体与数据工程',
    icon: 'brain-circuit',
    tags: ['FastAPI', 'Neo4j', 'FAISS', 'LLM', 'Python', 'Pandas'],
  },
]

export const timeline: TimelineEvent[] = [
  {
    year: '2024',
    place: '商丘师范学院',
    title: '数据科学与大数据技术',
    description: '主攻数据可视化与全栈方向',
  },
  {
    year: '2023',
    place: '前端开发实习',
    title: 'Vue 3 与数据可视化项目实战',
    description: '独立完成首个全栈项目,沉淀工程化思维',
  },
  {
    year: '2022',
    place: '开源社区',
    title: '首次向 ECharts 等开源库提交 PR',
    description: '积累代码审查与协作规范经验',
  },
  {
    year: '2021',
    place: '编程起步',
    title: '从 Python 数据分析到前端工程',
    description: '确定可视化与全栈为主攻方向',
  },
  {
    year: '2020',
    place: '大学入学',
    title: '计算机科学与技术',
    description: '开启代码生涯,从 C 语言到 Web 开发',
  },
]

export const stats = [
  { value: '10+', label: '项目' },
  { value: '30+', label: '技术标签' },
  { value: '24', label: '博客文章' },
  { value: '5', label: '编码年限' },
]

export const radarData = [
  { name: '前端工程', value: 95 },
  { name: '数据可视化', value: 85 },
  { name: 'AI 工程', value: 70 },
  { name: '后端开发', value: 75 },
  { name: '算法基础', value: 80 },
  { name: '产品设计', value: 65 },
]
