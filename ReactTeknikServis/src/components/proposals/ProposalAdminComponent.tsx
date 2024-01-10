import { EffectCallback, MouseEventHandler, useEffect, useState } from "react";
import axiosconfig from "../utils/axiosconfig";
import { Button, Row, Spinner, Table } from "react-bootstrap";
import { NavLink } from "react-router-dom";
import { IProposalAdminDto } from "../model/IProposalAdminDto";
import { getAxiosHeaders } from "../utils/Utils";

export default function ProposalsComponent()
{
    const [proposals, setProposals] = useState<IProposalAdminDto[]>([]);
    useEffect(initialize(), []);
     
    function initialize(): EffectCallback {
        return () =>{
            axiosconfig.get("/proposal/admin/getalldto", getAxiosHeaders()).then((response) =>{
                setProposals(response.data);  
            });
        };
    }

    function acceptPropose(id:any){
        return()=>{

            axiosconfig.put("/proposal/admin/updatetruestatus/"+id, null, getAxiosHeaders()).then(initialize())
        }
    }

    function rejectPropose(id:any){
        return()=>{
            axiosconfig.put("/proposal/admin/updatefalsestatus/"+id, null, getAxiosHeaders()).then(initialize())
        }
    }

    return(
        <div>
            {proposals.length===0 &&(
                <Row className="justify-context-center">
                    <div>NO MORE PROPOSAL HERE!</div>
                </Row>
            )}

            {proposals.length!=0 &&(
                <Table responsive striped bordered>
                    <thead className="table-dark"> {
                        <tr>
                            <th className="col-auto">ID</th>
                            <th className="col-auto">NOTE</th>
                            <th className="col-auto">PRICE</th>
                            <th className="col-auto">USER ID</th>
                            <th className="col-auto">USER NAME</th>
                            <th className="col-auto">PRODUCT ID</th>
                            <th className="col-auto">PRODUCT NAME</th>
                            <th className="col-auto">STATUS</th>
                            <th className="col-auto">ACCEPT</th>
                            <th className="col-auto">DECLINE</th>
                        </tr>
                    }</thead>
                    <tbody>
                        {proposals.map((propose, index)=>(
                            <tr key={index}>
                                <td className="col-auto">{propose.id}</td>
                                <td className="col-auto">{propose.note}</td>
                                <td className="col-auto">{propose.price}</td>
                                <td className="col-auto">{propose.user_id}</td>
                                <td className="col-auto">{propose.username}</td>
                                <td className="col-auto">{propose.product_id}</td>
                                <td className="col-auto">{propose.name}</td>
                                <td className="col-auto">{propose.status}</td>
                                <td className="col-auto">
                                    <button className="btn btn-outline-success" 
                                    onClick={acceptPropose(propose.id)}>
                                        ACCEPT
                                    </button>
                                </td>
                                <td className="col-auto">
                                    <button className="btn btn-outline-danger"
                                    onClick={rejectPropose(propose.id)}>
                                        DECLINE
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            )
            }
        </div>
    )
}