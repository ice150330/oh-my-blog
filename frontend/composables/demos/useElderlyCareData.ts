// ============================================================
// 养老保险分析系统 —— 静态数据
// ============================================================

export function useDashboardData() {
  const keyMetrics = { total_individuals: 68322, retirement_rate: 38.5, avg_replace_rate: 42.3, latest_dependency_ratio: 18.7 }
  const keyMetricsList = [
    { label: '个体总数', value: keyMetrics.total_individuals.toLocaleString(), unit: '人' },
    { label: '最新退休比例', value: keyMetrics.retirement_rate + '%', unit: '' },
    { label: '平均替代率', value: keyMetrics.avg_replace_rate + '%', unit: '' },
    { label: '最新赡养比', value: keyMetrics.latest_dependency_ratio + '%', unit: '' },
  ]
  const datasets = [
    { name: 'charls_2015_2023', description: '中国健康与养老追踪调查（CHARLS）2015-2023', records: 68322, fields: 47, time_range: '2015-2023', fields_detail: [
      { name: 'age', type: 'int', missing: '0.0%' }, { name: 'gender', type: 'category', missing: '0.0%' }, { name: 'edu_years', type: 'int', missing: '1.2%' },
      { name: 'urban', type: 'category', missing: '0.0%' }, { name: 'health', type: 'float', missing: '2.3%' }, { name: 'wealth', type: 'float', missing: '3.1%' },
    ]},
    { name: 'pension_fund', description: '全国养老保险基金收支数据 2010-2023', records: 14, fields: 12, time_range: '2010-2023', fields_detail: [
      { name: 'year', type: 'int', missing: '0.0%' }, { name: 'revenue', type: 'float', missing: '0.0%' }, { name: 'expenditure', type: 'float', missing: '0.0%' },
      { name: 'balance', type: 'float', missing: '0.0%' }, { name: 'participants', type: 'float', missing: '0.0%' },
    ]},
  ]
  return { keyMetricsList, datasets }
}

const YEARS = ['2010','2011','2012','2013','2014','2015','2016','2017','2018','2019','2020','2021','2022','2023']

export function usePopulationData() {
  return {
    demographics: { years: YEARS, series: [
      { name: '总人口', data: [134091,134735,135404,136072,136782,137462,138271,139008,139538,140005,141212,141260,141175,140967] },
      { name: '老年人口', data: [11894,12288,12714,13161,13624,14386,15003,15831,16658,17603,19064,20056,20978,21676] },
      { name: '劳动人口', data: [99938,100283,100403,100557,100469,100361,100260,100432,100056,98910,96871,96432,96289,96228] },
    ]},
    agingIndicators: { years: YEARS, series: [
      { name: '老年抚养比', data: [11.9,12.3,12.7,13.1,13.7,14.3,15.0,15.9,16.8,17.8,19.7,20.8,21.8,22.5] },
      { name: '老龄化率', data: [8.9,9.1,9.4,9.7,10.1,10.5,10.8,11.4,11.9,12.6,13.5,14.2,14.9,15.4] },
      { name: '出生率', data: [11.9,11.9,12.1,12.1,12.4,12.1,13.0,12.6,10.9,10.4,8.5,7.5,6.8,6.4] },
      { name: '死亡率', data: [7.1,7.1,7.2,7.2,7.2,7.1,7.1,7.1,7.1,7.1,7.1,7.2,7.4,7.9] },
    ]},
    lifeExpectancy: { years: YEARS, series: [
      { name: '男性预期寿命', data: [74.1,74.4,74.7,75.0,75.3,75.6,75.9,76.2,76.4,76.7,77.0,77.3,77.5,77.8] },
      { name: '女性预期寿命', data: [77.5,77.8,78.1,78.4,78.7,79.0,79.3,79.6,79.8,80.1,80.4,80.7,80.9,81.2] },
    ]},
    economyUrban: { years: YEARS, series: [
      { name: '城镇化率', data: [49.7,51.3,52.6,53.7,54.8,56.1,57.4,58.5,59.6,60.6,63.9,64.7,65.2,66.2] },
      { name: 'GDP增速', data: [10.6,9.6,7.9,7.8,7.4,7.0,6.8,6.9,6.7,6.0,2.2,8.4,3.0,5.2] },
    ]},
  }
}

export function useMicroData() {
  return {
    distributionMap: {
      gender: { labels: ['男','女'], values: [34200,34122] },
      urban: { labels: ['城镇','农村'], values: [41000,27322] },
      region: { labels: ['东部','中部','西部','东北'], values: [28000,18000,16000,6322] },
      industry: { labels: ['农林牧渔','制造业','建筑业','批发零售','交通运输','住宿餐饮','信息技术','金融','房地产','其他'], values: [8500,12000,6500,8000,4500,3800,5200,3800,3200,12822] },
    },
    retirementPattern: {
      age_retire: { ages: ['50-54','55-59','60-64','65-69','70-74','75+'], rates: [5.2,18.7,52.3,78.5,89.2,95.1] },
      gender_retire: { labels: ['男','女'], values: [42.3,35.1] },
      urban_retire: { labels: ['城镇','农村'], values: [45.2,28.7] },
      region_retire: { labels: ['东部','中部','西部','东北'], values: [48.5,35.2,30.8,42.1] },
      industry_retire: { labels: ['农林牧渔','制造业','建筑业','批发零售','交通运输','住宿餐饮','信息技术','金融','房地产','其他'], values: [55.2,48.3,52.1,42.5,50.3,45.8,38.2,35.6,40.1,43.2] },
    },
    healthRetire: { labels: ['很差','较差','一般','较好','很好'], values: [78.5,62.3,45.8,32.1,18.7] },
    incomeWealth: { wage_histogram: { labels: ['<1万','1-2万','2-3万','3-5万','5-8万','8-12万','12-20万','>20万'], values: [5200,12800,18500,15200,9800,4200,1800,822] } },
  }
}

export function useMacroData() {
  const fundTrend = { years: YEARS, series: [
    { name: '基金收入', data: [13420,16895,19757,22680,25310,29341,32195,35000,38300,42000,44300,48000,51000,55000] },
    { name: '基金支出', data: [10555,12765,15562,18471,21755,24600,27500,31000,34000,38000,42000,45000,49000,53000] },
    { name: '当期结余', data: [2865,4130,4195,4209,3555,4741,4695,4000,4300,4000,2300,3000,2000,2000] },
    { name: '累计结余', data: [15365,19495,23690,27899,31454,36195,40890,44890,49190,53190,55490,58490,60490,62490] },
  ]}
  const participants = { years: YEARS, series: [
    { name: '参保职工', data: [19400,21500,22900,24200,25500,26200,27800,29200,30100,31100,32800,34800,36700,38000] },
    { name: '参保离退休', data: [6300,6800,7400,8000,8590,9140,10100,11000,11800,12400,12800,13200,13600,14100] },
  ]}
  const keyRatios = { years: YEARS, series: [
    { name: '替代率', data: [44.5,43.2,42.8,42.1,41.5,41.0,40.5,40.0,39.5,39.0,38.5,38.0,37.5,37.0] },
    { name: '赡养比', data: [32.5,31.6,32.3,33.1,33.7,34.9,36.3,37.7,39.2,39.9,39.0,37.9,37.1,37.1] },
    { name: '缴费率', data: [28.0,28.0,28.0,28.0,28.0,28.0,28.0,28.0,28.0,24.0,24.0,24.0,24.0,24.0] },
  ]}
  function computeSustainability(params: { revenue_growth: number; expenditure_growth: number; years: number }) {
    const projections = []; let cumulative = 62490
    for (let i = 0; i < params.years; i++) {
      const year = 2024 + i
      const revenue = Math.round(55000 * Math.pow(1 + params.revenue_growth, i + 1))
      const expenditure = Math.round(53000 * Math.pow(1 + params.expenditure_growth, i + 1))
      const balance = revenue - expenditure; cumulative += balance
      projections.push({ year, revenue, expenditure, balance, cumulative })
    }
    return projections
  }
  return { fundTrend, participants, keyRatios, computeSustainability }
}

export function useMiningData() {
  return {
    correlation: {
      variables: ['年龄','性别','教育','健康','财富','工资','缴费率','城乡','退休','存活'],
      matrix: [
        [1.00,-0.02,-0.15,-0.35,0.12,0.08,0.05,0.18,0.62,-0.45],
        [-0.02,1.00,0.05,-0.08,-0.03,-0.12,-0.02,0.02,-0.15,0.08],
        [-0.15,0.05,1.00,0.22,0.35,0.42,0.18,0.25,-0.28,0.15],
        [-0.35,-0.08,0.22,1.00,0.15,0.18,0.08,0.12,-0.52,0.35],
        [0.12,-0.03,0.35,0.15,1.00,0.55,0.22,0.32,-0.18,0.12],
        [0.08,-0.12,0.42,0.18,0.55,1.00,0.35,0.28,-0.22,0.10],
        [0.05,-0.02,0.18,0.08,0.22,0.35,1.00,0.15,-0.12,0.05],
        [0.18,0.02,0.25,0.12,0.32,0.28,0.15,1.00,-0.25,0.18],
        [0.62,-0.15,-0.28,-0.52,-0.18,-0.22,-0.12,-0.25,1.00,-0.55],
        [-0.45,0.08,0.15,0.35,0.12,0.10,0.05,0.18,-0.55,1.00],
      ],
    },
    cluster: {
      silhouette_score: 0.58,
      features: ['age','edu_years','health','wealth','wage','pension_contrib','pension_benefit','contrib_rate'],
      cluster_centers: [
        { age:52, edu_years:9, health:3.2, wealth:15, wage:2.5, pension_contrib:0.6, pension_benefit:0, contrib_rate:0.08 },
        { age:58, edu_years:11, health:3.5, wealth:35, wage:5.0, pension_contrib:1.2, pension_benefit:0.8, contrib_rate:0.08 },
        { age:68, edu_years:8, health:2.8, wealth:20, wage:0, pension_contrib:0, pension_benefit:2.5, contrib_rate:0 },
      ],
      cluster_sizes: [28500,24800,15022],
    },
    prediction: {
      accuracy: 0.847, intercept: -2.35,
      feature_importance: [
        { feature:'age', importance:0.85 }, { feature:'health', importance:0.62 }, { feature:'wealth', importance:0.48 },
        { feature:'wage', importance:0.42 }, { feature:'edu_years', importance:0.35 }, { feature:'contrib_rate', importance:0.28 },
        { feature:'gender', importance:0.18 }, { feature:'urban', importance:0.12 },
      ],
    },
    FIELD_LABELS: { age:'年龄', edu_years:'受教育年限', health:'健康评分', wealth:'家庭净财富', wage:'年工资收入', pension_contrib:'养老保险缴费', pension_benefit:'养老金领取', contrib_rate:'个人缴费率', retired:'退休状态', dead:'存活状态', retire_age:'法定退休年龄' } as Record<string,string>,
    FEATURE_LABELS: { age:'年龄', gender:'性别', edu_years:'受教育年限', urban:'城乡', health:'健康评分', wealth:'家庭财富', wage:'年工资收入', contrib_rate:'个人缴费率' } as Record<string,string>,
  }
}
