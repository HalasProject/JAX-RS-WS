<template>
 <div class="flex flex-col max-w-full overflow-x-hidden shadow-md m-8">
	    
  <div v-if="state.showModal" class="main-modal fixed w-full h-100 inset-0 z-50 overflow-hidden flex justify-center items-center animated fadeIn faster"
		style="background: rgba(0,0,0,.7);">
		<div
			class="border border-teal-500 shadow-lg modal-container bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">
			<div class="modal-content py-4 text-left px-6">
				<!--Title-->
				<div class="flex justify-between items-center pb-3">
					<p class="text-2xl font-bold">Add Product</p>
					<div @click="closeModal()" class="modal-close cursor-pointer z-50">
						<svg class="fill-current text-black" xmlns="http://www.w3.org/2000/svg" width="18" height="18"
							viewBox="0 0 18 18">
							<path
								d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z">
							</path>
						</svg>
					</div>
				</div>
				<!--Body-->
				<div class="my-5">
			
        
            <div class="mb-4 relative">
                <label for="email" class="label absolute mb-0 -mt-2 pt-4 pl-3 leading-tighter text-gray-400 text-base mt-2 cursor-text">Product Name</label>
                <input v-model="state.product.name" class="input border border-gray-400 appearance-none rounded w-full px-3 py-3 pt-7 pb-2 focus focus:border-indigo-600 focus:outline-none active:outline-none active:border-indigo-600" id="email" type="text" autofocus>
            </div>
            <div class="mb-4 relative">
                <label for="password" class="label absolute mb-0 -mt-2 pt-4 pl-3 leading-tighter text-gray-400 text-base mt-2 cursor-text">Quantity</label>
                <input v-model="state.product.quantity" class="input border border-gray-400 appearance-none rounded w-full px-3 py-3 pt-7 pb-2 focus focus:border-indigo-600 focus:outline-none active:outline-none active:border-indigo-600" id="password" type="number" autofocus>
            </div>
            
       
				</div>
				<!--Footer-->
				<div class="flex justify-end pt-2">
					<button @click="closeModal()"
						class="focus:outline-none modal-close px-4 bg-gray-400 p-3 rounded-lg text-black hover:bg-gray-300">Cancel</button>
					<button v-if="state.mood !== 'update'" @click="addProduct()"
						class="focus:outline-none px-4 bg-green-500 p-3 ml-3 rounded-lg text-white hover:bg-teal-400">Add</button>
          <button v-if="state.mood == 'update'" @click="updateProduct()"
						class="focus:outline-none px-4 bg-green-500 p-3 ml-3 rounded-lg text-white hover:bg-teal-400">Update</button>
				</div>
			</div>
		</div>
	</div>
      
      <!-- Tools -->
	    <div class="grid grid-cols-1 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-6 bg-white p-6 space-y-2 md:space-y-0">
	        <div class="relative sm:col-span-12 md:col-span-3 lg:col-span-2">
	            <input
	                type="text"
                  v-model="state.search"
	                placeholder="Search by id: 14, 12 ,1"
	                class="block w-full px-8 py-2 border border-gray-300 placeholder-gray-500 text-gray-800 shadow-sm rounded-md focus:outline-none focus:ring-gray-500 focus:border-gray-500 focus:z-10 sm:text-sm"
	            />

	            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" class="absolute left-3 bottom-3 h-4 w-4 text-gray-500">
	                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
	            </svg>
	        </div>

	        <button @click="searchProduct()" class="bg-indigo-800 text-white text-xs font-semibold py-2 rounded-md border-0">Search</button>
	        <button @click="openModal('add')" class="bg-indigo-800 text-white text-xs font-semibold py-2 rounded-md border-0">Add Product</button>
          <button @click="syncProduct()" class="bg-indigo-800 text-white text-xs font-semibold py-2 rounded-md border-0">All Products</button>
	    </div>
	    <!-- End Tools -->

	    <table class="overflow-x-auto w-full bg-white">
	        <thead class="bg-blue-100 border-b border-gray-300">
	            <tr>
	                <th class="p-4 text-center text-sm font-medium text-gray-500">ID</th>
	                <th class="p-4 text-center text-sm font-medium text-gray-500">Name</th>
	                <th class="p-4 text-center text-sm font-medium text-gray-500">Quantity</th>
	                <th class="p-4 text-center text-sm font-medium text-gray-500">Actions</th>
	               
	            </tr>
	        </thead>
         
	        <tbody class="text-gray-600 text-sm divide-y divide-gray-300">
	            <tr v-for="product in state.products" class="bg-white font-medium text-sm divide-y divide-gray-200">
	                <td class="p-4 whitespace-nowrap">{{product.id}}</td>
	                <td class="p-4 whitespace-nowrap">{{product.name}}</td>
	                <td class="p-4 whitespace-nowrap">
	                    <span class="bg-indigo-100 text-indigo-600 text-xs font-semibold rounded-2xl py-1 px-4">{{product.quantity}}</span>
	                </td>
	               
	                <td class="p-4 flex justify-center whitespace-nowrap">
	                    <div class="flex space-x-1">
	                        <button @click="openModal('update',product)" class="border-2 border-indigo-200 rounded-md p-1">
	                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" class="h-4 w-4 text-indigo-500">
	                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />
	                            </svg>
	                        </button>

	                        <button @click="deleteProduct(product.id)" class="border-2 border-red-200 rounded-md p-1">
	                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" class="h-4 w-4 text-red-500">
	                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
	                            </svg>
	                        </button>
	                    </div>
	                </td>
	               
	            </tr>
	        
	        </tbody>
	    </table>
	</div>
</template>

<script setup>
import { defineProps, reactive, onMounted } from 'vue'
import axios from 'axios'
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css';
var notyf = new Notyf();


const state = reactive({
    count: 4,
    showModal: false,
    products: [],
    search:"",
    mood: "add",
    product: {
      id:0,
      name:"",
      quantity:0
    }
  })

  onMounted(()=>{
    axios.get(import.meta.env.VITE_API_URL).then((response)=>{
        state.products = response.data;
    })
  })

  function syncProduct(){
    console.log(state)
      axios.get(import.meta.env.VITE_API_URL).then((response)=>{
        state.products = response.data;
    })
  }

  function deleteProduct(id){
    axios.delete(`${import.meta.env.VITE_API_URL}/${id}`).then((response)=>{
      notyf.success(response.data);
      syncProduct();
    }).catch((error)=>{
      notyf.error(error.response.data);
    })
  }

  function updateProduct(){
      if (state.product.name.trim() !== "" && state.product.quantity !== 0){
       axios.put(`${import.meta.env.VITE_API_URL}/update`,state.product).then((response)=>{
          notyf.success(response.data);
          self.showModal = false;
          syncProduct();
          closeModal();
       }).catch((error)=>{
          notyf.error(error.response.data);
        })
     } else {
       notyf.error('Please use a valide information')
     }
  }

  function addProduct(){
     if (state.product.name.trim() !== "" && state.product.quantity !== 0){
       axios.post(`${import.meta.env.VITE_API_URL}/create`,state.product).then((response)=>{
          notyf.success(response.data);
          syncProduct();
          closeModal();
         
       }).catch((error)=>{
          notyf.error(error.response.data);
        })
     } else {
       notyf.error('Please use a valide information')
     }
  }

  function openModal(type = "add",product = null){
    resetProduct();
    if (type == "update"){
        state.mood = "update"
        state.product = {...product}
    } else {
        state.mood = "add"
    }
    state.showModal = true
	}

  function closeModal(){
    resetProduct()
    state.showModal = false;
  }

  function resetProduct(){
    state.product.name = ""
    state.product.quantity = 0
  }

  function searchProduct(){
    if (state.search.trim() === ""){
      return;
    }
    axios.get(`${import.meta.env.VITE_API_URL}/${state.search}`).then((response)=>{
      state.products = [response.data]
    }).catch((error)=>{
      notyf.error(error.response.data)
    })
  }

</script>

<style scoped>
    a {
      color: #42b983;
    }

		.animated {
			-webkit-animation-duration: 1s;
			animation-duration: 1s;
			-webkit-animation-fill-mode: both;
			animation-fill-mode: both;
		}

		.animated.faster {
			-webkit-animation-duration: 500ms;
			animation-duration: 500ms;
		}

		.fadeIn {
			-webkit-animation-name: fadeIn;
			animation-name: fadeIn;
		}

		.fadeOut {
			-webkit-animation-name: fadeOut;
			animation-name: fadeOut;
		}

		@keyframes fadeIn {
			from {
				opacity: 0;
			}

			to {
				opacity: 1;
			}
		}

		@keyframes fadeOut {
			from {
				opacity: 1;
			}

			to {
				opacity: 0;
			}
		}
</style>