<template>
  <div class="card-self-main">
    <div class="card-info" v-if="card !== null">
      <h2>Card info</h2>
      <div class="card-info-main-section">
        <div class="card-info-element">
          <h5>Number: </h5>
          <div class="user-card-number">{{ card.number }}</div>
        </div>
        <div class="card-info-element">
          <h5>User: </h5>
          <div class="user-card-owner">{{ card.owner }}</div>
        </div>
        <div class="card-info-element">
          <h5>Balance: </h5>
          <div class="user-card-balance">{{ card.balance }} ₽</div>
        </div>
      </div>
    </div>
    <h2>Operations: </h2>
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

    <h2>Balance conditions: </h2>

    <div id="chart" class="chart-section" >
      <apexchart type="area" height="550"
                 :options="chartOptions"
                 :series="series"
                 v-if="operations.length > 0"
      ></apexchart>
      <h4 v-if="operations.length === 0">There no one operation from this card</h4>
    </div>

    <h2>Operations history: </h2>

    <div class="table-of-operations" v-if="operations.length > 0">
        <b-table bordered hover
                 :items="operations"
                 id="operations-table"
                 :per-page="perPage"
                 :current-page="currentPage"
        ></b-table>
    </div>

    <h4 v-if="operations.length === 0" class="table-of-operations-empty-info">There no one operation from this card</h4>

    <div class="pagination-panel" v-if="operations.length > 0">
      <b-pagination
          v-if="operations.length > 10"
          v-model="currentPage"
          :total-rows="operations.length"
          :per-page="perPage"
          aria-controls="operations-table"
      ></b-pagination>
    </div>

  </div>
</template>

<script>
import axios from "axios";

let isSentAndReceived = false
let card = null
let newOperation = null

export default {
  name: "Card",
  data() {
    return {
      card: null,
      newOperation: null,

      selected: 'Top up',
      options: [
        {value: 'Withdraw', text: 'Withdraw from card'},
        {value: 'Top up', text: 'Top up card'},
      ],
      valueToOperate: null,

      operations: [],

      currentPage: 1,
      perPage: 10,

      balaceValues: [],
      balanceDateChanges: [],

      series: [{
        data: []
      }],
      chartOptions: {
        chart: {
          type: 'area',
          height: 500,
          zoom: {
            enabled: false
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: 'straight'
        },
        title: {
          text: 'Balance change',
          align: 'left'
        },
        labels: [],
        xaxis: {
          type: 'datetime',
        },
        yaxis: {
          opposite: true
        },
        legend: {
          horizontalAlign: 'left'
        }
      },
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
    executeOperation() {
      if (this.valueToOperate !== null) {
        if (this.selected === 'Withdraw') {
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

    checkAbilityToWithdraw() {
      if (this.card.balance < this.valueToOperate) {
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

    requestToWithDraw() {
      axios.post('/operation/create?cardNumber=' + this.card.number
          + '&type=Withdraw'
          + '&username=' + this.card.username
          + '&value=' + this.valueToOperate)
          .then(function (response) {
            isSentAndReceived = true

            newOperation = response.data

            console.log(response.data)
          })
          .catch(function (error) {
            console.log(error)
          })
      const interval = setInterval(() => {
        if (isSentAndReceived) {
          if (newOperation.cardNumber !== null) {
            this.newOperation = newOperation

            this.card.balance = Number( this.card.balance) - Number(this.valueToOperate)

            this.valueToOperate = ''

            this.operationLocalListUpdate()
            this.fillingTable()
            this.fillingChart()
          } else {
            this.toastCreation("Please, enter valid data")
          }

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

            newOperation = response.data

            console.log(response.data)
          })
          .catch(function (error) {
            console.log(error)
          })
      const interval = setInterval(() => {
        if (isSentAndReceived) {
          if (newOperation.cardNumber !== null) {
            this.newOperation = newOperation

            this.card.balance = Number( this.card.balance) + Number(this.valueToOperate)

            this.valueToOperate = ''

            this.operationLocalListUpdate()
            this.fillingTable()
            this.fillingChart()
          } else {
            this.toastCreation("Please, enter valid data")
          }

          clearInterval(interval)
        }
      }, 1000)
    },

    operationLocalListUpdate(){
      this.card.operationDTOList[this.card.operationDTOList.length] = {
        cardNumber: this.newOperation.cardNumber,
        time: this.newOperation.time,
        type: this.newOperation.type,
        username: this.newOperation.username,
        value: this.newOperation.value
      }
    },

    fillingTable() {
      this.operations = []
      if (this.card.operationDTOList.length > 0){
        for (let i = 0; i < this.card.operationDTOList.length; i++) {
          this.operations[i] = {
            Number: i,
            Type: this.card.operationDTOList[this.card.operationDTOList.length-1-i].type,
            Value: this.card.operationDTOList[this.card.operationDTOList.length-1-i].value,
            Time: this.card.operationDTOList[this.card.operationDTOList.length-1-i].time.toString().substring(0, 19).replaceAll("T", " "),
          }
        }
      }
    },

    fillingChart() {
      this.series = []

      this.balaceValues = []
      this.balanceDateChanges = []

      if (this.card.operationDTOList.length > 0){
        let tempBalance = 0
        for (let i = 0; i < this.card.operationDTOList.length; i++) {
          if (this.card.operationDTOList[i].type === 'Withdraw'){
            tempBalance -=  this.card.operationDTOList[i].value
          } else {
            tempBalance +=  this.card.operationDTOList[i].value
          }
          this.balaceValues[i] = tempBalance
          this.balanceDateChanges[i] = this.card.operationDTOList[i].time.toString().substring(0, 19).replaceAll("T", " ")
        }
        this.series[0] = {
          data: this.balaceValues
        }

        this.chartOptions = this.refreshChartOptions()
        this.chartOptions.labels = this.balanceDateChanges
      }
    },

    refreshChartOptions(){
      return {
        chartOptions: {
          chart: {
            type: 'area',
            height: 500,
            zoom: {
              enabled: true
            }
          },
          tooltip: {
            style: {
              fontSize: '12px',
              fontFamily: undefined,
              color: '#000'
            },
          },
          dataLabels: {
            enabled: false
          },
          stroke: {
            curve: 'straight'
          },
          title: {
            text: 'Balance change',
            align: 'left'
          },
          labels: this.balanceDateChanges,
          xaxis: {
            type: 'datetime'
          },
          yaxis: {
            opposite: true
          },
          legend: {
            horizontalAlign: 'left'
          }
        },
      }
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

        this.fillingTable()
        this.fillingChart()

        clearInterval(interval)
      }
    }, 100)
  }
}
</script>

<style scoped>
.card-self-main{
  max-height: 90vh;
  overflow: auto;
  padding: 25px;
}

.custom-select {
  height: 47px;
}

.card-info-main-section {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 55px;
}

.card-info-element {
  display: flex;
  flex-direction: row;
  align-items: baseline;
}

.card-operations {
  padding: 55px;
}

.table{
  color: white;
}

.table-of-operations{
  padding: 55px;
  border-radius: 45px;
}

.card-self-main::-webkit-scrollbar {
  width: 7px;
  height: 7px;
}

.card-self-main::-webkit-scrollbar-thumb {
  border-width: 1px 1px 1px 2px;
  border-color: #9f1b1b;
  background-color: #aaa;
  border-radius: 7px;
}

.card-self-main::-webkit-scrollbar-thumb:hover {
  border-width: 1px 1px 1px 2px;
  border-color: #555;
  background-color: #777;
}

.card-self-main::-webkit-scrollbar-track {
  border-width: 0;
}

.card-self-main::-webkit-scrollbar-track:hover {
  border-left: solid 1px #aaa;
  background-color: #eee;
}

.user-card-number{
  margin-left: 65px;
}

.user-card-owner{
  margin-left: 100px;
}

.user-card-balance{
  margin-left: 70px;
}

.chart-section{
  padding: 45px;
}

.table-of-operations-empty-info{
  margin-top: 35px;
}

.pagination-panel{
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>