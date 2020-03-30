/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { AccountTypeDetailComponent } from 'app/entities/account-type/account-type-detail.component';
import { AccountType } from 'app/shared/model/account-type.model';

describe('Component Tests', () => {
  describe('AccountType Management Detail Component', () => {
    let comp: AccountTypeDetailComponent;
    let fixture: ComponentFixture<AccountTypeDetailComponent>;
    const route = ({ data: of({ accountType: new AccountType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [AccountTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccountTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
