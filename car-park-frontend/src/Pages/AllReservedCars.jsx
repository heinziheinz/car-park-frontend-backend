import {useEffect, useState} from "react";
import {jswTokenFetch} from "../Utilities/jswTokenFetch.js";
import {Link} from "react-router-dom";

const AllReservedCars = () => {

    const [allReservedCars, setAllReservedCars] = useState([]);

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
            const responseAllReservedCars = await jswTokenFetch(`/reservation/get-all-reserved-cars/id/${userData.userId}`, options, headers);
            console.log("allReservedCars " + responseAllReservedCars);
            console.log(responseAllReservedCars);
            if (responseAllReservedCars.ok) {
                console.log("ALL OK");
                const allReservedCarsParsed = await responseAllReservedCars.json()
                console.log(allReservedCarsParsed);
                setAllReservedCars(allReservedCarsParsed);
            }
        })();
    }, []);


    return (
        <>
        <div>Your reserved cars</div>
        <div className="EmployeeTable">
            <table>
                <thead>
                <tr>
                    <th name="name">name</th>
                    <th name="type">price</th>
                    <th/>
                </tr>
                </thead>
                <tbody>
                {allReservedCars.map((car) => (
                    <tr key={car.id}>
                        <td>{car.typeName}</td>
                        <td>{car.price}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
        </>
    )
}
export default AllReservedCars;