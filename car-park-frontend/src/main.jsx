import React from 'react'
import ReactDOM from 'react-dom/client'
import Layout from "./Pages/Layout.jsx";
import ErrorPage from "./Pages/ErrorPage.jsx";
//import CarCreator from "./Pages/CarCreator.jsx"
import CarForm from "./Components/CarForm.jsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";


const router = createBrowserRouter([
    {
        path: "/",
        element: <Layout />,
        errorElement: <ErrorPage />,
        children: [
            {
                path: "/",
                element: <CarForm />,
            }
        ],
    },
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
)
