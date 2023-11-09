import {fetchAuthenticated} from "../../Utilities/api.js";

export async function fetchCarHouse(id) {
    const carHouseToBeUpdated = await fetchAuthenticated(`/carhouses/id/${id}`, {
        method: "GET",
    })
    console.log("carHouseToBeUpdated")
    console.log(carHouseToBeUpdated)
    if (carHouseToBeUpdated.ok) {
        return  await carHouseToBeUpdated.json();
    } else {
        throw new Error("Error with Fetch call allCarsInCarHouse");
    }
}

export async function fetchAllCarsNotAllocatedToCArHouse(currentPageNotAllocated){
    const allCarsNotAllocatedToACarHouse = await fetchAuthenticated(`/cars/all-cars-not-allocated-to-a-carhouse?page=${currentPageNotAllocated}&size=10`, {
        method: "GET"
    });
    console.log("allCarsNotAllocatedToACarHouse")
    console.log(allCarsNotAllocatedToACarHouse)
    if (allCarsNotAllocatedToACarHouse.ok) {
        return await allCarsNotAllocatedToACarHouse.json();

    } else {
        throw new Error("Error with Fetch call allCarsInCarHouse");
    }

}

export async function fetchAllCarsInCarHouse(id,currentPageInCarHouse){
    const allCarsInCarHouse = await fetchAuthenticated(`/cars/find-all-cars-with-in-carhouse/${id}?page=${currentPageInCarHouse}&size=10`, {
        method: "GET"
    });
    console.log("allCarsInCarHouse")
    console.log(allCarsInCarHouse)
    if (allCarsInCarHouse.ok) {
        return await allCarsInCarHouse.json();

    } else {
        throw new Error("Error with Fetch call allCarsInCarHouse");
    }
}