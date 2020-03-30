/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { UserConnectionComponent } from 'app/entities/user-connection/user-connection.component';
import { UserConnectionService } from 'app/entities/user-connection/user-connection.service';
import { UserConnection } from 'app/shared/model/user-connection.model';

describe('Component Tests', () => {
  describe('UserConnection Management Component', () => {
    let comp: UserConnectionComponent;
    let fixture: ComponentFixture<UserConnectionComponent>;
    let service: UserConnectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [UserConnectionComponent],
        providers: []
      })
        .overrideTemplate(UserConnectionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserConnectionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserConnectionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserConnection(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userConnections[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
