import {useEffect, useState} from "react";
import {fetchAuthenticated} from "../../Utilities/api.js";

const userCarHouse = () => {
    const [loading, setLoading] = useState(true);
    const [carHouses, setCarHouses] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
    const [carHouseDeleted, setCarHouseDeleted] = useState(false);

    useEffect(() => {

        (async () => {
            try {
                const allCarHouses = await fetchAuthenticated(`/carhouses?page=0&size=10`, {
                    method: "GET"
                });
                if (allCarHouses.ok) {
                    const allCarHousesParsed = await allCarHouses.json();
                    setLoading(false);
                    setTotalPages(allCarHousesParsed?.totalPages);
                    setCarHouses(allCarHousesParsed.content)
                } else {
                    throw new Error("All carts error");
                }
            } catch (err) {
                console.error(err);
            }
        })();
    }, [carHouseDeleted]);

    const handleDelete = async (id) => {
        console.log(id);
        console.log("delete Car with id:" + id);
        const options = {
            method: "DELETE",
        };
        const userData = JSON.parse(localStorage.getItem("userdata"));
        const headers = {
            "Authorization": `Bearer ${userData.jwt}`,
            "Content-Type": "application/json"
        };
        try {
            const deletedCarHouse = await fetchAuthenticated(`/carhouses/${id}`, {
                method: "DELETE"
            });
            console.log("deletedCarHouse " + deletedCarHouse);
            if (deletedCarHouse.ok) {
                const deletedCarHouseParsed = await deletedCarHouse.json();
                console.log("After");
                setCarHouseDeleted(!carHouseDeleted);

                console.log(deletedCarHouseParsed);
            }
        } catch (err) {
            console.log(err);
        }

    };

    const flipThePage = (event) => {
        console.log(event.target.name)
        let myCurrentPage;
        if (event.target.name === "plus") {
            myCurrentPage = currentPage + 1;
        }
        if (event.target.name === "minus") {
            myCurrentPage = currentPage - 1;
        }
        setCurrentPage(myCurrentPage);
    }

    return {handleDelete, loading, carHouses, totalPages, currentPage, flipThePage};
}
export default userCarHouse;