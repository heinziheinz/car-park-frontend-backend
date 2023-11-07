import {useEffect} from "react";
import {useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {jwtTokenFetch} from "../Utilities/jwtTokenFetch.js";
import Loading from "../Components/Loading/Loading.jsx";
import CarForm from "../Components/CarForm/CarForm.jsx";


const CarUpdater = () => {
    const [car, setCar] = useState(null);
    const [loading, setLoading] = useState(true);
    const {id} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        (async () => {
            console.log("Update Use Effect");
            const options = {
                method: "GET",
            };
            const userData = JSON.parse(localStorage.getItem("userdata"));
            const headers = {
                "Authorization": `Bearer ${userData.jwt}`,
                "Content-Type": "application/json"
            };
            try {
                //http://localhost:8080/cars/id/1
                const carToBeUpdated = await jwtTokenFetch(`/cars/id/${id}`, options, headers)
                console.log("Car to be Updated " + carToBeUpdated);
                console.log(carToBeUpdated);
                if (carToBeUpdated.ok) {
                    const carToBeUpdatedParsed = await carToBeUpdated.json();
                    console.log(carToBeUpdatedParsed)
                    setCar(carToBeUpdatedParsed);
                    setLoading(false);

                }
            } catch (err) {
                console.log(err);
            }
        })();
    }, [id]);

    const updateCarHandler = async (car) => {
        console.log("Update Car Handler");
        console.log(car);
        const options = {
            method: "PUT",
            body: JSON.stringify(car)
        };
        const userData = JSON.parse(localStorage.getItem("userdata"));
        const headers = {
            "Authorization": `Bearer ${userData.jwt}`,
            "Content-Type": "application/json"
        };
        try {
            //http://localhost:8080/cars/id/1
            const updatedCar = await jwtTokenFetch(`/cars/${id}`, options, headers)
            console.log("updatedCar " + updatedCar);
            console.log(updatedCar);
            if (updatedCar.ok) {
                console.log(updatedCar)
                const updatedCarParsed =  await updatedCar.json();
               console.log(updatedCarParsed);
                navigate("/car-list")
            }
        } catch (err) {
            console.log(err);
        }

    }

    if (loading) {
        return <Loading/>;
    }


    return <CarForm
        carHouse={car}
        onSave={updateCarHandler}
    />;
}
export default CarUpdater;