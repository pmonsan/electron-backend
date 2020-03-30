import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  ContractOppositionComponent,
  ContractOppositionDetailComponent,
  ContractOppositionUpdateComponent,
  ContractOppositionDeletePopupComponent,
  ContractOppositionDeleteDialogComponent,
  contractOppositionRoute,
  contractOppositionPopupRoute
} from './';

const ENTITY_STATES = [...contractOppositionRoute, ...contractOppositionPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ContractOppositionComponent,
    ContractOppositionDetailComponent,
    ContractOppositionUpdateComponent,
    ContractOppositionDeleteDialogComponent,
    ContractOppositionDeletePopupComponent
  ],
  entryComponents: [
    ContractOppositionComponent,
    ContractOppositionUpdateComponent,
    ContractOppositionDeleteDialogComponent,
    ContractOppositionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayContractOppositionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
