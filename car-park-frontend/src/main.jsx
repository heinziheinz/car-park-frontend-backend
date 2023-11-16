import React, {useState} from 'react'
import ReactDOM from 'react-dom/client'
import Layout from "./Pages/Layout/Layout.jsx";
import ErrorPage from "./Pages/ErrorPage.jsx";
//import CarCreator from "./Pages/CarCreator.jsx"
import Login from "./Pages/Login.jsx";
import LogOut from "./Pages/LogOut.jsx";
import CarForm from "./Components/CarForm/CarForm.jsx";
import CarSearch from "./Pages/CarSearch/CarSearch.jsx";
import UserList from "./Pages/UserList.jsx";
import CarCreator from "./Pages/CarCreator.jsx";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import FetchUser from "./Pages/FetchUser.jsx";
import CarBooker from "./Pages/CarBooker.jsx";
import AllReservedCars from "./Pages/AllReservedCars.jsx";
import BookingConfirmation from "./Pages/BookingConfirmation.jsx";
import CarList from "./Pages/CarList/CarList.jsx";
import CarUpdater from "./Pages/CarUpdater.jsx";
import CarHouseCreator from "./Pages/CarHouseCreator.jsx";
import CarHouseTable from "./Components/CarHouseTable/CarHouseTable.jsx";
import CarHouseList from "./Pages/CarHouse/CarHouseList.jsx";
import CarHouseUpdate from "./Pages/CarHouseUpdate/CarHouseUpdate.jsx";
import Subscribe from "./Pages/Subcribe/Subscribe.jsx";
import Redirect from "./Components/Redirect/Redirect.jsx";
import UserUpdate from "./Pages/UserUpdate.jsx";
import UserCars from "./Pages/UsersCars/UserCars.jsx";
import ReservationUpdate from "./Pages/ReservationUpdate.jsx";
import "./index.css";


const router = createBrowserRouter([
    {
        path: "/",
        element: <Layout/>,
        errorElement: <ErrorPage/>,
        children: [
            {
                path: "/login",
                element: <Login/>,
            },
            {
                path: "/logout",
                element: <LogOut/>,
            },
            {
                path: "/subscribe",
                element: <Subscribe/>,
            },
            {
                path: "/add-a-car",
                element: <CarCreator/>,
            },
            {
                path: "/car/update/:id",
                element: <CarUpdater/>,
            },
            {
                path: "/car-list",
                element: <CarList/>,
            },
            {
                path: "/search-car",
                element: <CarSearch/>,
            },
            {
                path: "/show-all-users",
                element: <UserList/>,
            },
            {
                path: "/fetch-user",
                element: <FetchUser/>
            },
            {
                path: "/car/book/:id",
                element: <CarBooker/>,
            },
            {
                path: "/all-reserved-cars",
                element: <AllReservedCars/>,
            },
            {
                path: "/booking-confirmation/:id/:startDate/:endDate/:carTypename/:carPrice/:userID",
                element: <BookingConfirmation/>,
            },
            {
                path: "/car-house-creator",
                element: <CarHouseCreator/>,
            },
            {
                path: "/car-house-list",
                element: <CarHouseList/>,
            },
            {
                path: "/car-house-update/:id",
                element: <CarHouseUpdate/>,
            },
            {
                path: "/redirect/:id",
                element: <Redirect/>,
            },
            {
                path: "/update/user/:id",
                element: <UserUpdate/>,
            },
            {
                path: "/users/all-cars-reserved/:id",
                element: <UserCars/>,
            },
            {
                path: "/change-reservations/:reservationId",
                element: <ReservationUpdate/>,
            }


        ],
    },
]);

export const LogginInContext = React.createContext();
export const UserRoleContext = React.createContext();

function App() {
    const [loggedIn, setLoggedIn] = useState(false);
    const [userRole, setUserRole] = useState('USER');
    console.log(loggedIn)
    return (
        < LogginInContext.Provider value={{loggedIn, setLoggedIn}}>
            <UserRoleContext.Provider value={{userRole, setUserRole}}>
                <RouterProvider router={router}/>
            </UserRoleContext.Provider>
        </ LogginInContext.Provider>
    )

}

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <App/>
    </React.StrictMode>,
)


