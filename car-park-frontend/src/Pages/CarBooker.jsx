import {useEffect} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {fetchAuthenticated} from "../Utilities/api.js";

const CarBooker = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    useEffect(() => {
        (async () => {
            try {
                const options = {
                    method: "GET",
                };
                    const bookedCar = await fetchAuthenticated(`/cars/id/${id}`, options)
                if (bookedCar.ok) {
                    const bookedCarParsed = await bookedCar.json();
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