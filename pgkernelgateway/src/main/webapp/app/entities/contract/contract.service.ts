import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContract } from 'app/shared/model/contract.model';

type EntityResponseType = HttpResponse<IContract>;
type EntityArrayResponseType = HttpResponse<IContract[]>;

@Injectable({ providedIn: 'root' })
export class ContractService {
  public resourceUrl = SERVER_API_URL + 'api/contracts';

  constructor(protected http: HttpClient) {}

  create(contract: IContract): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contract);
    return this.http
      .post<IContract>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contract: IContract): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contract);
    return this.http
      .put<IContract>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContract>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContract[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(contract: IContract): IContract {
    const copy: IContract = Object.assign({}, contract, {
      creationDate: contract.creationDate != null && contract.creationDate.isValid() ? contract.creationDate.toJSON() : null,
      modificationDate:
        contract.modificationDate != null && contract.modificationDate.isValid() ? contract.modificationDate.toJSON() : null,
      validationDate: contract.validationDate != null && contract.validationDate.isValid() ? contract.validationDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
      res.body.modificationDate = res.body.modificationDate != null ? moment(res.body.modificationDate) : null;
      res.body.validationDate = res.body.validationDate != null ? moment(res.body.validationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((contract: IContract) => {
        contract.creationDate = contract.creationDate != null ? moment(contract.creationDate) : null;
        contract.modificationDate = contract.modificationDate != null ? moment(contract.modificationDate) : null;
        contract.validationDate = contract.validationDate != null ? moment(contract.validationDate) : null;
      });
    }
    return res;
  }
}
