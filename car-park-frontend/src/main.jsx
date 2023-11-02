import React from 'react'
import ReactDOM from 'react-dom/client'
import Layout from "./Pages/Layout/Layout.jsx";
import ErrorPage from "./Pages/ErrorPage.jsx";
//import CarCreator from "./Pages/CarCreator.jsx"
import Login from "./Pages/Login.jsx";
import CarForm from "./Components/CarForm.jsx";
import CarList from "./Pages/CarList.jsx";
import UserList from "./Pages/UserList.jsx";
import CarCreator from "./Pages/CarCreator.jsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import FetchUser from "./Pages/FetchUser.jsx";




const router = createBrowserRouter([
    {
        path: "/",
        element: <Layout />,
        errorElement: <ErrorPage />,
        children: [
            {
                path: "/login",
                element: <Login />,
            },
            {
                path: "/add-a-car",
                element: <CarCreator/>,
            },
            {
                path: "/",
                element: <CarList />,
            },
            {
                path: "/show-all-users",
                element: <UserList />,
            },
            {
                path:"/fetch-user",
                element:<FetchUser/>
            }

        ],
    },
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
)
