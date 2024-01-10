import {
    Button,
    Card,
    FormControl,
    Form,
    FormSelect,
    Table,
} from "react-bootstrap";

import { useNavigate } from "react-router-dom";
import { getAxiosHeaders } from "../utils/Utils";
import { IProduct } from "../model/IProduct";
import axiosconfig from "../utils/axiosconfig";
import { IProposal } from "../model/IProposal";
import { useEffect, useState } from "react";

export default function TakeBookingComponent() {
    console.log(localStorage.getItem("userjwt"));

    const [note, setNote] = useState("");
    const [price, setPrice] = useState(-1);
    const [productId, setProductId] = useState(-1);
    const [products, setProducts] = useState<IProduct[]>([]);
    const [proposals, setProposals] = useState<IProposal[]>([]);

    const navigate = useNavigate();

    useEffect(() => {

        axiosconfig.get("/product/getAllProduct").then((response) => {
            try {
                setProducts(response.data);
            } catch (err) { }
        });

        axiosconfig
            .get("/proposal/user/getAll", getAxiosHeaders())
            .then((response) => {
                try {
                    setProposals(response.data);
                } catch (err) { }
            });
    }, []);

    function handleSubmit(event) {
        const token = localStorage.getItem("userjwt");
        event.preventDefault();

        const sendData = {
            note: note,
            price: price,
            product_id: productId,
        };

        axiosconfig
            .post("proposal/user/saveWithId", sendData, getAxiosHeaders())
            .then((res) => {
                console.log(getAxiosHeaders);
                if (res.status === 200) {
                    navigate("/proposal");
                }
            })
            .catch((error) => {
                console.error("There was an error!", error);
            });
    }

    const getStatusColor = (status) => {
        switch (status) {
            case 'bekliyor':
                return 'yellow'; // Sarı
            case 'onaylandı':
                return 'lightgreen'; // Yeşil
            case 'reddedildi':
                return 'pink'; // Kırmızı
            default:
                return 'black'; // Varsayılan renk
        }
    };

    return (
        <Card>
            <Card.Body className="shadow">
                <Form method="post" onSubmit={handleSubmit}>
                    <FormControl
                        type="text"
                        placeholder="Note"
                        onChange={(e) => setNote(e.target.value)}
                    />

                    <FormControl
                        type="number"
                        placeholder="Price"
                        onChange={(e) => setPrice(Number.parseInt(e.target.value))}
                    />

                    <FormSelect
                        onChange={(e) => setProductId(Number.parseInt(e.target.value))}
                    >
                        <option key={-1} value={-1}>
                            Choose Product
                        </option>
                        {products.map((product, i) => (
                            <option key={i} value={product.id}>
                                {product.name}
                            </option>
                        ))}
                    </FormSelect>
                    <br />
                    <Button variant="outline-success" type="submit">
                        Submit
                    </Button>
                </Form>
            </Card.Body>
            <hr />
            <center>
                <h1 style={{ fontWeight: "bold", color:"#006600", marginBottom:"20px"}}>Proposal List</h1>
            </center>

            <Table hover>
                <thead>
                    <tr>
                        <th style={{ color:"#006600"}}>Proposal</th>
                        <th style={{ color:"#006600"}}>Product</th>
                        <th style={{ color:"#006600"}}>Price</th>
                        <th style={{ color:"#006600"}}>Note</th>
                        <th style={{ color:"#006600"}}>Status</th>
                    </tr>
                </thead>
                <tbody>
                    {proposals.map((proposals, i) => (
                        <tr key={i} >
                            <td style={{ backgroundColor: getStatusColor(proposals.status) }}>{proposals.id}</td>
                            <td style={{ backgroundColor: getStatusColor(proposals.status) }}>{products.find(product => product.id === proposals.product_id)?.name}</td>
                            <td style={{ backgroundColor: getStatusColor(proposals.status) }}>{proposals.price}</td>
                            <td style={{ backgroundColor: getStatusColor(proposals.status) }}>{proposals.note}</td>
                            <td style={{ backgroundColor: getStatusColor(proposals.status) }}>{proposals.status}</td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </Card>
    );
}
