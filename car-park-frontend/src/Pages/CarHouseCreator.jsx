import {useState, useEffect} from "react";
import {useNavigate} from "react-router-dom";
import CarHouseForm from "../Components/CarHouseForm/CarHouseForm.jsx";
import Loading from "../Components/Loading/Loading.jsx";
import {fetchAuthenticated} from "./../Utilities/api.js";

const CarHouseCreator = () => {
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleCreateCarHouse = async (carHouse) => {
        try {
            const createdCarHouse = await fetchAuthenticated(`/carhouses`, {
                method: "POST",
                body: JSON.stringify(carHouse)
            });

            if (createdCarHouse.ok) {
                const createdCarHouseParsed = await createdCarHouse.json();
                navigate("/car-house-list")

            } else {
                throw new Error("CarHouseError");
            }
        } catch (err) {
            console.error(err);
        }

    };
    if (loading) {
        return <Loading/>;
    }
    return <CarHouseForm
        onSave={handleCreateCarHouse}
    />;
}
export default CarHouseCreator;