import {useNavigate, useParams} from "react-router-dom";
import {fetchAuthenticated} from "../Utilities/api.js";

const BookingConfirmation = () => {
    const {id, startDate, endDate, carTypename, carPrice, userID} = useParams();
    const navigate = useNavigate();


    const bookCarHandler = async (event) => {
        event.preventDefault();
        try {
            const options = {
                method: "POST",
            };
            const bookedCar = await fetchAuthenticated(`/cars/${id}/user/${userID}/${startDate}/${endDate}`, options);
            if (bookedCar.ok) {
                navigate("/all-reserved-cars");
            }
        } catch (err) {
            console.log(err);
        }

    }
    return (
        <>
            <h2>Booking Confírmation</h2>
            <h4> {carTypename}</h4>
            <h5> {carPrice}</h5>
            <p>Please confirm your Booking for</p>
            <button onClick={bookCarHandler}>Yes I want to rent the car</button>
        </>
    );
}

export default BookingConfirmation;