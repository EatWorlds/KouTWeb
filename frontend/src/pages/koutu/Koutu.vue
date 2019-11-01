<template>
    <div>
        <KoutuHeader></KoutuHeader>
        <div class="main">
            <KoutuSide></KoutuSide>
            <KoutuShow></KoutuShow>
        </div>
    </div>
</template>

<script>
import KoutuHeader from './components/Header'
import KoutuSide from './components/Side'
import KoutuShow from './components/Show'

export default {
  name: 'Koutu',
  components: {
      KoutuHeader,
      KoutuSide,
      KoutuShow
  },
}
</script>

<style  scoped>
    .main{
        display: flex;
        flex-direction: row;
    }
</style>

