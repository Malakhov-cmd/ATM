<template>
  <div>
    <div class="card-info" v-if="card !== null">
      <div class="card-info-element">
        <h5>Number: </h5>
        <div class="user-card-number">{{ card.number }}</div>
      </div>
      <div class="card-info-element">
        <h5>User: </h5>
        <div class="user-card-number">{{ card.owner }}</div>
      </div>
    </div>
    <h2>Операции: </h2>
    <div class="card-operations">
      <b-input-group>
        <b-form-group id="operation-select-type-group">
          <b-form-select v-model="selected" :options="options"></b-form-select>
        </b-form-group>

        <b-form-input
            class="input-value-to-operate"
            id="input-value-to-operate-id"
            v-model="valueToOperate"
            placeholder="Enter value"
            required
            type="number"
        ></b-form-input>

        <b-button variant="success" v-on:click="executeOperation">
          Ok
        </b-button>
      </b-input-group>
    </div>
  </div>
</template>

<script>
import axios from "axios";

let isSentAndReceived = false
let card = null

export default {
  name: "Card",
  data() {
    return {
      card: null,

      selected: null,
      options: [
        {value: 'Withdraw', text: 'Withdraw from card'},
        {value: 'Top up', text: 'Top up card'},
      ],
      valueToOperate: null
    }
  },
  computed: {
    cardNumber() {
      return this.$route.params.cardNumber;
    },
    userName() {
      return this.$route.params.userName;
    },
  },
  methods: {
    executeOperation(){
      if (this.valueToOperate !== null) {
        if (this.selected == 'Withdraw') {
          if (this.checkAbilityToWithdraw()) {
            this.requestToWithDraw()
          }
        } else {
          this.requestToTopUp()
        }
      } else {
        this.toastCreation("Enter something first")
      }
    },

    checkAbilityToWithdraw(){
      if (this.card.balance < this.valueToOperate){
        this.toastCreation("You dont have enough money")
        return false
      }
      return true
    },

    toastCreation(text) {
      this.$toasted.error(text, {
        position: 'top-right',
        duration: 5000,
        fullWidth: false,
        action: {
          text: 'Cancel',
          onClick: (e, toastObject) => {
            toastObject.goAway(0);
          }
        },
      })
    },

    requestToWithDraw(){
      axios.post('/operation/create?cardNumber=' + this.card.number
          + '&type=Withdraw'
          + '&username=' + this.card.username
          + '&value=' + this.valueToOperate)
          .then(function (response) {
            isSentAndReceived = true

            console.log(response.data)
          })
          .catch(function (error) {
            console.log(error)
          })
      const interval = setInterval(() => {
        if (isSentAndReceived) {

          clearInterval(interval)
        }
      }, 1000)
    },

    requestToTopUp() {
      axios.post('/operation/create?cardNumber=' + this.card.number
          + '&type=TopUp'
          + '&username=' + this.card.username
          + '&value=' + this.valueToOperate)
          .then(function (response) {
            isSentAndReceived = true

            console.log(response.data)
          })
          .catch(function (error) {
            console.log(error)
          })
      const interval = setInterval(() => {
        if (isSentAndReceived) {

          clearInterval(interval)
        }
      }, 1000)
    }
  },
  mounted() {
    axios.get('/card/get', {
      params: {
        username: frontendData.username,
        cardNumber: this.cardNumber
      }
    })
        .then(function (response) {
          isSentAndReceived = true
          card = response.data

          console.log(response.data)
        })
        .catch(function (error) {
          console.log(error)
        })
    const interval = setInterval(() => {
      if (isSentAndReceived) {
        this.card = card

        clearInterval(interval)
      }
    }, 100)
  }
}
</script>

<style scoped>
.custom-select{
  height: 47px;
}
</style>