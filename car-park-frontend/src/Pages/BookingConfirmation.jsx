import {useEffect} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {jswTokenFetch} from "../Utilities/jswTokenFetch.js";

const BookingConfirmation = () => {
    const {id} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
      /*  (async () => {
            const options = {
                method: "POST",
            };
            const userData = JSON.parse(localStorage.getItem("userdata"));
            const headers = {
                "Authorization": `Bearer ${userData.jwt}`,
                "Content-Type": "application/json"
            };
            try {
                //http://localhost:8080/cars/${id}/user/${userData.id}/2026-12-20/2026-12-21
                const bookedCar = await jswTokenFetch(`/cars/id/${id}`, options, headers)
                console.log("Booked Car " + bookedCar);
                console.log(bookedCar);
                if (bookedCar.ok) {
                    const bookedCarParsed = await bookedCar.json();
                    console.log("bookedCarParsed" + bookedCarParsed);
                    navigate(`/all-reserved-cars`)
                }
            } catch (err) {
                console.log(err);
            }
        })();*/
    }, []);
    return "Booking Confírmation";
}

export default BookingConfirmation;