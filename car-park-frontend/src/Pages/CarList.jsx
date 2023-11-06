import {useEffect, useState} from "react";
import {jwtTokenFetch} from "../Utilities/jwtTokenFetch.js";
import CarTableCompleteList from "../Components/CarTableCompleteList.jsx";
import Login from "./Login.jsx";

const CarList = () => {
    const [loading, setLoading] = useState(true);
    const [cars, setCars] = useState([]);

    const handleDelete = (id) => {
        console.log("delete Car with id:" + id);
    };

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
                const allCars = await jwtTokenFetch(`/cars`, options, headers)
                console.log("All Cars " + allCars);
                console.log(allCars);
                if (allCars.ok) {
                    const allCarsParsed = await allCars.json();
                    setLoading(false);
                    console.log(allCarsParsed);
                    setCars(allCarsParsed.content)
                }
            } catch (err) {
                console.log(err);
            }
        })();
    }, []);

    if (loading) {
        return <Login/>;
    }
    return <CarTableCompleteList cars={cars} onDelete={handleDelete}/>;
}
export default CarList;