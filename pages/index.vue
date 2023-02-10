<template>
  <div>
    <NuxtLogo/>
  </div>
</template>

<script>
export default {
  name: 'IndexPage',
  async asyncData({$axios}) {
    let [data] = await Promise.all([
      $axios.get(`/admin/house/housearea/list`),
    ])
    return {data}
  },
  created() {
    this.$axios.get(`/admin/house/housearea/list`).then(response => {
      console.log(response)
    })
  }
}
</script>
