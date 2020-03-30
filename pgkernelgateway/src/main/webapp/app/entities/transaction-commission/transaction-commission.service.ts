import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransactionCommission } from 'app/shared/model/transaction-commission.model';

type EntityResponseType = HttpResponse<ITransactionCommission>;
type EntityArrayResponseType = HttpResponse<ITransactionCommission[]>;

@Injectable({ providedIn: 'root' })
export class TransactionCommissionService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-commissions';

  constructor(protected http: HttpClient) {}

  create(transactionCommission: ITransactionCommission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionCommission);
    return this.http
      .post<ITransactionCommission>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transactionCommission: ITransactionCommission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionCommission);
    return this.http
      .put<ITransactionCommission>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransactionCommission>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactionCommission[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(transactionCommission: ITransactionCommission): ITransactionCommission {
    const copy: ITransactionCommission = Object.assign({}, transactionCommission, {
      dateCreation:
        transactionCommission.dateCreation != null && transactionCommission.dateCreation.isValid()
          ? transactionCommission.dateCreation.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreation = res.body.dateCreation != null ? moment(res.body.dateCreation) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transactionCommission: ITransactionCommission) => {
        transactionCommission.dateCreation = transactionCommission.dateCreation != null ? moment(transactionCommission.dateCreation) : null;
      });
    }
    return res;
  }
}
