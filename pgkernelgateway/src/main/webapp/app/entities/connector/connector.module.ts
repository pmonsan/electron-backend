import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  ConnectorComponent,
  ConnectorDetailComponent,
  ConnectorUpdateComponent,
  ConnectorDeletePopupComponent,
  ConnectorDeleteDialogComponent,
  connectorRoute,
  connectorPopupRoute
} from './';

const ENTITY_STATES = [...connectorRoute, ...connectorPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ConnectorComponent,
    ConnectorDetailComponent,
    ConnectorUpdateComponent,
    ConnectorDeleteDialogComponent,
    ConnectorDeletePopupComponent
  ],
  entryComponents: [ConnectorComponent, ConnectorUpdateComponent, ConnectorDeleteDialogComponent, ConnectorDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayConnectorModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
