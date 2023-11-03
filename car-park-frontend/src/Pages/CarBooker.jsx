import {useEffect} from "react";
import {jswTokenFetch} from "../Utilities/jswTokenFetch.js";
import {useNavigate, useParams} from "react-router-dom";
import {Buffer} from "buffer";

const CarBooker = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    console.log("my id " + id);
    useEffect(() => {
        (async () => {
            const options = {
                method: "GET",
            };
            const userData = JSON.parse(localStorage.getItem("userdata"));
            const headers = {
                "Authorization": `Bearer ${userData.jwt}`,
                "Content-Type": "application/json"
            };
            try {
                const bookedCar = await jswTokenFetch(`/cars/id/${id}`, options, headers)
                console.log("Booked Car " + bookedCar);
                console.log(bookedCar);
                if (bookedCar.ok) {
                    const bookedCarParsed = await bookedCar.json();
                    console.log("bookedCarParsed" + bookedCarParsed);
                    navigate(`/booking-confirmation/${bookedCarParsed.id}`)
                }
            } catch (err) {
                console.log(err);
            }
        })();
    }, []);

    return <h2>CarBooker</h2>
}
export default CarBooker;