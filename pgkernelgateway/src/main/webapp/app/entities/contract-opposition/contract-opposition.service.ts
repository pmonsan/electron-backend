import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContractOpposition } from 'app/shared/model/contract-opposition.model';

type EntityResponseType = HttpResponse<IContractOpposition>;
type EntityArrayResponseType = HttpResponse<IContractOpposition[]>;

@Injectable({ providedIn: 'root' })
export class ContractOppositionService {
  public resourceUrl = SERVER_API_URL + 'api/contract-oppositions';

  constructor(protected http: HttpClient) {}

  create(contractOpposition: IContractOpposition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contractOpposition);
    return this.http
      .post<IContractOpposition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contractOpposition: IContractOpposition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contractOpposition);
    return this.http
      .put<IContractOpposition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContractOpposition>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContractOpposition[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(contractOpposition: IContractOpposition): IContractOpposition {
    const copy: IContractOpposition = Object.assign({}, contractOpposition, {
      oppositionDate:
        contractOpposition.oppositionDate != null && contractOpposition.oppositionDate.isValid()
          ? contractOpposition.oppositionDate.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.oppositionDate = res.body.oppositionDate != null ? moment(res.body.oppositionDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((contractOpposition: IContractOpposition) => {
        contractOpposition.oppositionDate = contractOpposition.oppositionDate != null ? moment(contractOpposition.oppositionDate) : null;
      });
    }
    return res;
  }
}
