/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceAuthenticationDetailComponent } from 'app/entities/service-authentication/service-authentication-detail.component';
import { ServiceAuthentication } from 'app/shared/model/service-authentication.model';

describe('Component Tests', () => {
  describe('ServiceAuthentication Management Detail Component', () => {
    let comp: ServiceAuthenticationDetailComponent;
    let fixture: ComponentFixture<ServiceAuthenticationDetailComponent>;
    const route = ({ data: of({ serviceAuthentication: new ServiceAuthentication(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceAuthenticationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceAuthenticationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceAuthenticationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceAuthentication).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
