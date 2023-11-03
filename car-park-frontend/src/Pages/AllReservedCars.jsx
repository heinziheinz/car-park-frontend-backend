import {useEffect} from "react";
import {jswTokenFetch} from "../Utilities/jswTokenFetch.js";

const AllReservedCars = () => {

    useEffect(() => {
        (async () => {

            const options = {
                method: "GET",
            };
            const userData = JSON.parse(localStorage.getItem("userdata"));
            const headers = {
                "Authorization": `Bearer ${userData.jwt}`,
                "Content-Type": "application/json"
            };///reservation/get-all-reserved-cars/id/
            const bookedCars = await jswTokenFetch(`/reservation/get-all-reserved-cars/id/${userData.userId}`, options, headers);
            console.log("Booked Car " + bookedCars);
            console.log(bookedCars);
            if (bookedCars.ok) {
                console.log("ALL OK");
                const bookedCarsParsed = await bookedCars.json()
                console.log(bookedCarsParsed);
            }
        })();
    });


    return <h2>Booking Confirmation</h2>
}
export default AllReservedCars;