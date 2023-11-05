import {useEffect} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {jswTokenFetch} from "../Utilities/jswTokenFetch.js";

const BookingConfirmation = () => {
    const {id, startDate, endDate, carTypename, carPrice} = useParams();
    console.log(id)
    console.log(startDate)
    console.log(endDate)
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

    const bookCarHandler = async (event) => {
        event.preventDefault();
        try {
            const options = {
                method: "POST",
            };
            // http://localhost:8080/cars/2/user/1/2026-12-20/2026-12-21
            const userData = JSON.parse(localStorage.getItem("userdata"));
            const headers = {
                "Authorization": `Bearer ${userData.jwt}`,
                "Content-Type": "application/json"
            };
            const bookedCar = await jswTokenFetch(`/cars/${id}/user/1/${startDate}/${endDate}`, options, headers)
            if (bookedCar.ok) {
                console.log("Your Car has been booked");
                const bookedCarParsed = await bookedCar.json();
                console.log(bookedCarParsed);
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