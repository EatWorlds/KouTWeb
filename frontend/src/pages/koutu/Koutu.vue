<template>
    <div>
        <KoutuHeader></KoutuHeader>
        <KoutuSide></KoutuSide>
    </div>
</template>

<script>
import KoutuHeader from './components/Header'
import KoutuSide from './components/Side'

export default {
  name: 'Koutu',
  components: {
      KoutuHeader,
      KoutuSide
  },
}
</script>

