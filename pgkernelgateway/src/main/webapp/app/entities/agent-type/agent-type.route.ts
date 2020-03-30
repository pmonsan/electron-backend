import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AgentType } from 'app/shared/model/agent-type.model';
import { AgentTypeService } from './agent-type.service';
import { AgentTypeComponent } from './agent-type.component';
import { AgentTypeDetailComponent } from './agent-type-detail.component';
import { AgentTypeUpdateComponent } from './agent-type-update.component';
import { AgentTypeDeletePopupComponent } from './agent-type-delete-dialog.component';
import { IAgentType } from 'app/shared/model/agent-type.model';

@Injectable({ providedIn: 'root' })
export class AgentTypeResolve implements Resolve<IAgentType> {
  constructor(private service: AgentTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAgentType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AgentType>) => response.ok),
        map((agentType: HttpResponse<AgentType>) => agentType.body)
      );
    }
    return of(new AgentType());
  }
}

export const agentTypeRoute: Routes = [
  {
    path: '',
    component: AgentTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.agentType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgentTypeDetailComponent,
    resolve: {
      agentType: AgentTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.agentType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgentTypeUpdateComponent,
    resolve: {
      agentType: AgentTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.agentType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgentTypeUpdateComponent,
    resolve: {
      agentType: AgentTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.agentType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const agentTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AgentTypeDeletePopupComponent,
    resolve: {
      agentType: AgentTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.agentType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
