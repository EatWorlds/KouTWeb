<template>
    <div>
        <HomeHeader></HomeHeader>
        <HomeBanner></HomeBanner>
        <div style="height:1200px">

        </div>
    </div>
</template>

<script>
import HomeHeader from './components/Header'
import HomeBanner from './components/Banner'

export default {
  name: 'Home',
  components: {
      HomeHeader,
      HomeBanner
  },
}
</script>

