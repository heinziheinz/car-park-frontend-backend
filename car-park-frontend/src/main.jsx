import React, {useState} from 'react'
import ReactDOM from 'react-dom/client'
import Layout from "./Pages/Layout/Layout.jsx";
import ErrorPage from "./Pages/ErrorPage.jsx";
//import CarCreator from "./Pages/CarCreator.jsx"
import Login from "./Pages/Login.jsx";
import LogOut from "./Pages/LogOut.jsx";
import CarForm from "./Components/CarForm.jsx";
import CarSearch from "./Pages/CarSearch.jsx";
import UserList from "./Pages/UserList.jsx";
import CarCreator from "./Pages/CarCreator.jsx";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import FetchUser from "./Pages/FetchUser.jsx";
import CarBooker from "./Pages/CarBooker.jsx";
import AllReservedCars from "./Pages/AllReservedCars.jsx";
import BookingConfirmation from "./Pages/BookingConfirmation.jsx";


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
                path: "/add-a-car",
                element: <CarCreator/>,
            },
            {
                path: "/",
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
                element: <CarBooker />,
            },
            {
                path: "/all-reserved-cars",
                element: <AllReservedCars />,
            },
            {
                path: "/booking-confirmation/:id/:startDate/:endDate/:carTypename/:carPrice",
                element: <BookingConfirmation />,
            },


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
            <UserRoleContext.Provider value={{ userRole, setUserRole }}>
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


